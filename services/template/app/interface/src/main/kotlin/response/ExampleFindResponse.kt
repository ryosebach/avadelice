package response

import resource.ErrorResource
import resource.ExampleResource

sealed class ExampleFindResponse {
    data class Success(val example: ExampleResource) : ExampleFindResponse()
    data class BadRequest(val error: ErrorResource) : ExampleFindResponse()
    data class NotFound(val error: ErrorResource) : ExampleFindResponse()
}
