package response

import response.error.Error
import response.resource.ExampleResource

sealed class ExampleFindListResponse {
    data class Success(val exampleList: List<ExampleResource>) : ExampleFindListResponse()
    data class BadRequest(val error: Error) : ExampleFindListResponse()
    data class NotFound(val error: Error) : ExampleFindListResponse()
}
