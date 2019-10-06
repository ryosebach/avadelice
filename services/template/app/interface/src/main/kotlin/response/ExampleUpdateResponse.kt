package response

import resource.ErrorResource
import resource.ExampleResource

sealed class ExampleUpdateResponse {
    data class Success(val example: ExampleResource) : ExampleUpdateResponse()
    data class BadRequest(val error: ErrorResource) : ExampleUpdateResponse()
}
