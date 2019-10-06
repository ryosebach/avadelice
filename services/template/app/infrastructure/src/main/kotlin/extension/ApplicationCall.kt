package extension

import adapter.Controller
import adapter.Input
import adapter.Request
import adapter.Response
import io.ktor.application.ApplicationCall
import io.ktor.features.origin
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.host
import io.ktor.request.httpMethod
import io.ktor.request.httpVersion
import io.ktor.request.receiveOrNull
import io.ktor.request.uri
import io.ktor.response.respond
import resource.ErrorResource.ErrorType

fun ApplicationCall.toAccessLog(status: HttpStatusCode): String {
    return """
        forwardedfor:${this.request.headers[HttpHeaders.XForwardedFor] ?: "-"}
            host:${this.request.host()}
            method:${this.request.httpMethod.value}
            protocol:${this.request.httpVersion}
            referer:${this.request.headers[HttpHeaders.Referrer] ?: "-"}
            remote_host:${this.request.origin.remoteHost}
            status:${status.value}
            ua:${this.request.headers[HttpHeaders.UserAgent] ?: "-"}
            uri:${this.request.uri}
            uuid:${this.request.headers[HttpHeaders.XRequestId] ?: "-"}
    """.trimIndent().replace("\n", "")
}

fun ApplicationCall.customFormat(): String {
    return when (val status = this.response.status() ?: HttpStatusCode.NotFound) {
        HttpStatusCode.Found ->
            "${this.toAccessLog(status)} -> ${this.response.headers[HttpHeaders.Location]}"
        else ->
            this.toAccessLog(status)
    }
}

/**
 * 指定されたコントローラを処理する
 */
suspend inline fun <Params : Input, reified Content : Input, Resource : Any>
    ApplicationCall.process(controller: Controller<Params, Content, Resource>, params: Params? = null) {
    params?.let {
        val violations = it.validate()
        if (!violations.isValid()) {
            this.respond(HttpStatusCode.BadRequest, violations.toResource())
        }
    }

    val content = this.receiveOrNull<Content>()
    content?.let {
        val violations = it.validate()
        if (!violations.isValid()) {
            this.respond(HttpStatusCode.BadRequest, violations.toResource())
        }
    }

    when (val response = controller.proceed(Request(params, content))) {
        is Response.Success
        -> this.respond(HttpStatusCode.OK, response.resource)
        is Response.Failure -> {
            when (response.resource.type) {
                ErrorType.MALFORMED_INPUT,
                ErrorType.VALIDATION_ERROR
                -> this.respond(HttpStatusCode.BadRequest, response.resource)
                ErrorType.NOT_FOUND
                -> this.respond(HttpStatusCode.NotFound, response.resource)
                ErrorType.SYSTEM_ERROR
                -> this.respond(HttpStatusCode.InternalServerError, response.resource)
            }
        }
    }
}
