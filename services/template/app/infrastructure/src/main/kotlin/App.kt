
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import extension.customFormat
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.application.log
import io.ktor.features.CORS
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.request.path
import io.ktor.response.respond
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import module.InfrastructureModule
import module.ModuleBuilder
import org.koin.ktor.ext.installKoin
import org.slf4j.event.Level
import response.error.Error
import route.root

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.main() {
    installKoin(ModuleBuilder.modules(InfrastructureModule.module(environment.config)))

    install(ContentNegotiation) {
        jackson {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        }
    }

    install(CallLogging) {
        level = Level.INFO
        filter { call -> !call.request.path().startsWith("/v1/health") }
        format { call -> call.customFormat() }
    }

    install(StatusPages) {
        exception<ParameterConversionException> { cause ->
            log.debug(cause.message, cause)
            val message = cause.message ?: throw IllegalArgumentException("bad request message must not be null.")
            call.respond(HttpStatusCode.BadRequest, Error(message))
        }
        exception<Throwable> { cause ->
            log.error(cause.message, cause)
            call.respond(HttpStatusCode.InternalServerError, Error("Internal Server Error."))
        }
    }

    install(Locations)

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        anyHost()
    }

    routing {
        root()
    }
}
