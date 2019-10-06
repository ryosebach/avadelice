package request

import adapter.Input
import adapter.Violations
import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.core.Validator
import extension.toViolations

data class ExampleFindListParams(
    val limit: Int?,
    val offset: Int?,
    val includeDisabled: Boolean?
) : Input {
    companion object {
        val VALIDATOR: Validator<ExampleFindListParams> = ValidatorBuilder.of<ExampleFindListParams>()
            .konstraint(ExampleFindListParams::limit) {
                greaterThan(0).lessThan(100)
            }
            .konstraint(ExampleFindListParams::offset) {
                greaterThanOrEqual(0)
            }
            .build()
    }

    override fun validate(): Violations {
        return VALIDATOR.validate(this).toViolations()
    }
}
