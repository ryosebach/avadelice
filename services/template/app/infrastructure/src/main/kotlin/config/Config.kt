package config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import extension.customFormat
import io.ktor.application.ApplicationEnvironment
import io.ktor.application.call
import io.ktor.features.*
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module.Module
import org.slf4j.event.Level
import resource.ErrorResource
import resource.ErrorResource.ErrorType
import java.time.Duration

class Config(private val environment: ApplicationEnvironment) {

    /**
     * CORS の設定
     * http://ktor.io/servers/features/cors.html
     */
    val cors: CORS.Configuration.() -> Unit = {
        method(HttpMethod.Options)
        method(HttpMethod.Post)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        anyHost()
        allowCredentials = true
        maxAge = Duration.ofDays(1)
    }

    /**
     * https://ktor.io/servers/features/call-logging.html
     * CallLoggin の設定
     */
    val callLogging: CallLogging.Configuration.() -> Unit = {
        level = Level.INFO
        filter { call -> !call.request.path().startsWith("/v1/health") }
        format { call -> call.customFormat() }
    }

    /**
     * 通信の gzip 圧縮に関する設定
     * https://ktor.io/servers/features/compression.html
     */
    val compression: Compression.Configuration.() -> Unit = {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 10.0
            minimumSize(1024) // condition
        }
    }

    /**
     * Content-type に応じたコンテンツ変換の設定
     * https://ktor.io/servers/features/content-negotiation.html
     */
    val contentNegotiation: ContentNegotiation.Configuration.() -> Unit = {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        }
    }

    /**
     * 例外処理によるエラーページのカスタマイズ設定
     * https://ktor.io/servers/features/status-pages.html
     */
    @KtorExperimentalAPI
    val statusPages: StatusPages.Configuration.() -> Unit = {
        exception<ParameterConversionException> { cause ->
            environment.log.debug(cause.message, cause)
            call.respond(HttpStatusCode.BadRequest, ErrorResource(ErrorType.MALFORMED_INPUT, cause.message ?: ""))
        }
        exception<JsonMappingException> { cause ->
            environment.log.debug(cause.message, cause)
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResource(ErrorType.MALFORMED_INPUT, cause.cause?.message ?: cause.message ?: "")
            )
        }
        exception<Throwable> { cause ->
            environment.log.error(cause.message, cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResource(ErrorType.SYSTEM_ERROR, cause.message ?: "internal server error")
            )
        }
    }

    @io.ktor.util.KtorExperimentalAPI
    fun module(): Module = org.koin.dsl.module.module {
        single("database.master.url") { environment.config.property("database.master.url").getString() }
        single("database.master.driver") { environment.config.property("database.master.driver").getString() }
        single("database.master.user") { environment.config.property("database.master.user").getString() }
        single("database.master.password") { environment.config.property("database.master.password").getString() }
        single("database.master.connectionTimeout") { environment.config.property("database.master.connectionTimeout").getString().toLong() }
        single("database.master.idleTimeout") { environment.config.property("database.master.idleTimeout").getString().toLong() }
        single("database.master.maxLifetime") { environment.config.property("database.master.maxLifetime").getString().toLong() }
        single("database.master.minimumIdle") { environment.config.property("database.master.minimumIdle").getString().toInt() }
        single("database.master.maximumPoolSize") { environment.config.property("database.master.maximumPoolSize").getString().toInt() }
        single("database.master.prepStmtCacheSize") { environment.config.property("database.master.prepStmtCacheSize").getString().toInt() }
        single("database.master.prepStmtCacheSqlLimit") { environment.config.property("database.master.prepStmtCacheSqlLimit").getString().toInt() }
        single("database.slave.url") { environment.config.property("database.slave.url").getString() }
        single("database.slave.driver") { environment.config.property("database.slave.driver").getString() }
        single("database.slave.user") { environment.config.property("database.slave.user").getString() }
        single("database.slave.password") { environment.config.property("database.slave.password").getString() }
        single("database.slave.connectionTimeout") { environment.config.property("database.slave.connectionTimeout").getString().toLong() }
        single("database.slave.idleTimeout") { environment.config.property("database.slave.idleTimeout").getString().toLong() }
        single("database.slave.maxLifetime") { environment.config.property("database.slave.maxLifetime").getString().toLong() }
        single("database.slave.minimumIdle") { environment.config.property("database.slave.minimumIdle").getString().toInt() }
        single("database.slave.maximumPoolSize") { environment.config.property("database.slave.maximumPoolSize").getString().toInt() }
        single("database.slave.prepStmtCacheSize") { environment.config.property("database.slave.prepStmtCacheSize").getString().toInt() }
        single("database.slave.prepStmtCacheSqlLimit") { environment.config.property("database.slave.prepStmtCacheSqlLimit").getString().toInt() }
        single("api.stone.baseUrl") { environment.config.property("api.stone.baseUrl").getString() }
        single("dynamodb.prefix") { environment.config.property("dynamodb.prefix").getString() }
        single("dynamodb.endpoint") { environment.config.property("dynamodb.endpoint").getString() }
        single("dynamodb.region") { environment.config.property("dynamodb.region").getString() }
    }
}
