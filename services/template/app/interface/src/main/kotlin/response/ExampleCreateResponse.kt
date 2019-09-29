package response

import response.error.Error
import response.resource.ExampleResource

sealed class ExampleCreateResponse {
    data class Success(val example: ExampleResource) : ExampleCreateResponse()
    data class BadRequest(val error: Error) : ExampleCreateResponse()
}
