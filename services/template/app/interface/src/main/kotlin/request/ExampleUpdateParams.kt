package request

import adapter.Input
import adapter.Violations
import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.core.Validator
import extension.toViolations

data class ExampleUpdateParams(
    val exampleKey: String
) : Input {
    companion object {
        val validator: Validator<ExampleUpdateParams> = ValidatorBuilder.of<ExampleUpdateParams>()
            .konstraint(ExampleUpdateParams::exampleKey) {
                notNull()
            }
            .build()
    }

    override fun validate(): Violations {
        return validator.validate(this).toViolations()
    }
}
