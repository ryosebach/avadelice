package input

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import request.ExampleFindRequest

@KtorExperimentalLocationsAPI
@Location("/{exampleKey}")
data class ExampleFindInput(
    val exampleKey: String
) {
    fun toRequest(): ExampleFindRequest {
        return ExampleFindRequest(
            exampleKey
        )
    }
}
