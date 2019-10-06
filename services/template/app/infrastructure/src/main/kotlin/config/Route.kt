package config

import controller.ExampleCreateController
import controller.ExampleFindListController
import controller.ExampleFindOneController
import controller.ExampleUpdateController
import extension.process
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.locations.put
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.koin.ktor.ext.inject
import request.ExampleFindListParams
import request.ExampleFindOneParams
import request.ExampleUpdateParams

@KtorExperimentalLocationsAPI
fun Routing.root() {
    val exampleFindOneController: ExampleFindOneController by inject()
    val exampleFindListController: ExampleFindListController by inject()
    val exampleCreateController: ExampleCreateController by inject()
    val exampleUpdateController: ExampleUpdateController by inject()

    val health = mapOf("status" to "OK")

    route("v1") {
        get("/health") { call.respond(HttpStatusCode.OK, health) }
        get<Examples.List> { location -> call.process(exampleFindListController, location.toParams()) }
        get<Examples.One> { location -> call.process(exampleFindOneController, location.toParams()) }
        post<Examples> { call.process(exampleCreateController) }
        put<Examples.Update> { location -> call.process(exampleUpdateController, location.toParams()) }
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

    @Location("/{exampleKey}")
    data class Update(val exampleKey: String) {
        fun toParams(): ExampleUpdateParams {
            return ExampleUpdateParams(exampleKey)
        }
    }
}
