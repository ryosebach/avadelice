package result

import entity.Example

sealed class ExampleCreateResult {
    data class Success(val example: Example) : ExampleCreateResult()
    object BadRequest : ExampleCreateResult()
}
