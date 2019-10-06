package response

import resource.ExampleResource

sealed class ExampleFindListResponse {
    data class Success(val exampleList: List<ExampleResource>) : ExampleFindListResponse()
}
