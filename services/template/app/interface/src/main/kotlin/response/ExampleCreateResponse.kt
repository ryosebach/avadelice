package response

import resource.ErrorResource
import resource.ExampleResource

sealed class ExampleCreateResponse {
    data class Success(val example: ExampleResource) : ExampleCreateResponse()
    data class BadRequest(val error: ErrorResource) : ExampleCreateResponse()
}
