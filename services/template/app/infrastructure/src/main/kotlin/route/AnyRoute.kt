package route

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.routing.Routing
import io.ktor.routing.route
import route.v1.examples
import route.v1.health

@KtorExperimentalLocationsAPI
fun Routing.root() {
    route("v1") {
        health()
        examples()
    }
}
