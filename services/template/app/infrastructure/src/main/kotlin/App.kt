
import config.Config
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.*
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Locations
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import module.ModuleBuilder
import org.koin.ktor.ext.installKoin
import config.root

@KtorExperimentalAPI
@KtorExperimentalLocationsAPI
fun Application.main() {
    val config = Config(environment)

    installKoin(ModuleBuilder.modules(config.module()))

    install(ContentNegotiation, config.contentNegotiation)
    install(CORS, config.cors)
    install(CallLogging, config.callLogging)
    install(StatusPages, config.statusPages)
    install(Compression, config.compression)
    install(Locations)

    routing {
        root()
    }
}
