package result

import entity.Example

sealed class ExampleUpdateResult {
    data class Success(val example: Example) : ExampleUpdateResult()
    object BadRequest : ExampleUpdateResult()
}
