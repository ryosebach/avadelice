package result

import entity.Example

sealed class ExampleFindResult {
    data class Success(val example: Example) : ExampleFindResult()
    object NotFound : ExampleFindResult()
}
