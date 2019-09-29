package route.v1

import controller.ExampleController
import input.ExampleCreateInput
import input.ExampleFindInput
import input.ExampleFindListInput
import input.ExampleUpdateInput
import input.body.ExampleCreateBody
import input.body.ExampleUpdateBody
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.get
import io.ktor.locations.post
import io.ktor.locations.put
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import org.koin.ktor.ext.inject
import response.ExampleCreateResponse
import response.ExampleFindListResponse
import response.ExampleFindResponse
import response.ExampleUpdateResponse

@KtorExperimentalLocationsAPI
fun Route.examples() {
    val exampleController: ExampleController by inject()

    route("/examples") {
        get<ExampleFindInput> { input ->
            when (val response = exampleController.one(input.toRequest())) {
                is ExampleFindResponse.Success ->
                    call.respond(HttpStatusCode.OK, response.example)
                is ExampleFindResponse.BadRequest ->
                    call.respond(HttpStatusCode.BadRequest, response.error)
                is ExampleFindResponse.NotFound ->
                    call.respond(HttpStatusCode.NotFound, response.error)
            }
        }
        get<ExampleFindListInput> { input ->
            when (val response = exampleController.findList(input.toRequest())) {
                is ExampleFindListResponse.Success ->
                    call.respond(HttpStatusCode.OK, response.exampleList)
                is ExampleFindListResponse.BadRequest ->
                    call.respond(HttpStatusCode.BadRequest, response.error)
                is ExampleFindListResponse.NotFound ->
                    call.respond(HttpStatusCode.NotFound, response.error)
            }
        }
        post<ExampleCreateInput> { input ->
            val body = call.receive<ExampleCreateBody>()
            when (val response = exampleController.create(input.toRequest(body))) {
                is ExampleCreateResponse.Success ->
                    call.respond(HttpStatusCode.OK, response.example)
                is ExampleCreateResponse.BadRequest ->
                    call.respond(HttpStatusCode.BadRequest, response.error)
            }
        }
        put<ExampleUpdateInput> { input ->
            val body = call.receive<ExampleUpdateBody>()
            when (val response = exampleController.update(input.toRequest(body))) {
                is ExampleUpdateResponse.Success ->
                    call.respond(HttpStatusCode.OK, response.example)
                is ExampleUpdateResponse.BadRequest ->
                    call.respond(HttpStatusCode.BadRequest, response.error)
            }
        }
    }
}
