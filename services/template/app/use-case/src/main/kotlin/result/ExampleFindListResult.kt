package result

import entity.Example

sealed class ExampleFindListResult {
    data class Success(val exampleList: List<Example>) : ExampleFindListResult()
    object NotFound : ExampleFindListResult()
}
