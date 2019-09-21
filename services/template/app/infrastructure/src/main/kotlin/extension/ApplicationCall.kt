package extension

import io.ktor.application.ApplicationCall
import io.ktor.features.origin
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.request.host
import io.ktor.request.httpMethod
import io.ktor.request.httpVersion
import io.ktor.request.uri

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
