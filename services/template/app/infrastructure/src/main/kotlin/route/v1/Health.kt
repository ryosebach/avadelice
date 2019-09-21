package route.v1

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route

@KtorExperimentalLocationsAPI
fun Route.health() {
    route("/health") {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }
    }
}
