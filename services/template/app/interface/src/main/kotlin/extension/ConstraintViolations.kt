package extension

import am.ik.yavi.core.ConstraintViolations
import adapter.Violations
import resource.ErrorResource
import resource.ErrorResource.ErrorType

fun ConstraintViolations.toViolations(): Violations {
    val self = this
    return object : Violations {
        override fun isValid() = self.isValid

        override fun toResource() = ErrorResource(
            type = ErrorType.VALIDATION_ERROR,
            message = self.joinToString { it.message() }
        )
    }
}
