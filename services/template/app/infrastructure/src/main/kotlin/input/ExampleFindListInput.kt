package input

import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import request.ExampleFindListRequest

@KtorExperimentalLocationsAPI
@Location("/")
data class ExampleFindListInput(
    val limit: Int? = null,
    val offset: Int? = null,
    val include_disabled: Boolean? = false
) {
    fun toRequest(): ExampleFindListRequest {
        return ExampleFindListRequest(
            limit = limit,
            offset = offset,
            includeDisabled = include_disabled
        )
    }
}
