package request

import adapter.Input
import adapter.Violations
import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.core.Validator
import extension.toViolations

data class ExampleCreateContent(
    val exampleKey: String,
    val nameJa: String,
    val nameEn: String,
    val nameKo: String,
    val nameZh: String,
    val enabled: Boolean
) : Input {
    companion object {
        val validator: Validator<ExampleCreateContent> = ValidatorBuilder.of<ExampleCreateContent>()
            .konstraint(ExampleCreateContent::exampleKey) {
                greaterThanOrEqual(1).lessThanOrEqual(64)
            }
            .konstraint(ExampleCreateContent::nameJa) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleCreateContent::nameEn) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleCreateContent::nameKo) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleCreateContent::nameZh) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .build()
    }

    override fun validate(): Violations {
        return validator.validate(this).toViolations()
    }
}
