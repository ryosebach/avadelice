package response

import response.error.Error
import response.resource.ExampleResource

sealed class ExampleUpdateResponse {
    data class Success(val example: ExampleResource) : ExampleUpdateResponse()
    data class BadRequest(val error: Error) : ExampleUpdateResponse()
}
