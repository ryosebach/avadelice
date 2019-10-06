package request

import adapter.Input
import adapter.Violations
import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.core.Validator
import extension.toViolations

data class ExampleFindOneParams(
    val exampleKey: String
) : Input {
    companion object {
        val VALIDATOR: Validator<ExampleFindOneParams> = ValidatorBuilder.of<ExampleFindOneParams>()
            .konstraint(ExampleFindOneParams::exampleKey) {
                notNull()
            }
            .build()
    }

    override fun validate(): Violations {
        return VALIDATOR.validate(this).toViolations()
    }
}
