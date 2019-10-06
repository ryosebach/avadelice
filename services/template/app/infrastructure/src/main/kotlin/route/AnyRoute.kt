package route

import controller.ExampleFindListController
import controller.ExampleFindOneController
import extension.process
import io.ktor.application.call
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Routing
import io.ktor.routing.route
import org.koin.ktor.ext.inject
import request.ExampleFindListParams
import request.ExampleFindOneParams
import route.v1.examples
import route.v1.health

@KtorExperimentalLocationsAPI
fun Routing.root() {
    val exampleFindOneController: ExampleFindOneController by inject()
    val exampleFindListController: ExampleFindListController by inject()

    route("v1") {
        health()
        examples()
        get<Examples.List> { location -> call.process(exampleFindListController, location.toParams()) }
        get<Examples.One> { location -> call.process(exampleFindOneController, location.toParams()) }
    }
}

@KtorExperimentalLocationsAPI
@Location("/examples")
class Examples {

    data class List(val limit: Int = 10, val offset: Int = 0, val include_disabled: Boolean = false) {
        fun toParams(): ExampleFindListParams {
            return ExampleFindListParams(limit, offset, include_disabled)
        }
    }

    @Location("/{exampleKey}")
    data class One(val exampleKey: String) {
        fun toParams(): ExampleFindOneParams {
            return ExampleFindOneParams(exampleKey)
        }
    }
}
