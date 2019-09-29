package response

import response.error.Error
import response.resource.ExampleResource

sealed class ExampleFindResponse {
    data class Success(val example: ExampleResource) : ExampleFindResponse()
    data class BadRequest(val error: Error) : ExampleFindResponse()
    data class NotFound(val error: Error) : ExampleFindResponse()
}
