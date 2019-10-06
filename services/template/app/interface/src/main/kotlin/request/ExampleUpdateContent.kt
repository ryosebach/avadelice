package request

import adapter.Input
import adapter.Violations
import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint
import am.ik.yavi.core.Validator
import extension.toViolations

data class ExampleUpdateContent(
    val nameJa: String?,
    val nameEn: String?,
    val nameKo: String?,
    val nameZh: String?,
    val enabled: Boolean?
) : Input {
    companion object {
        val validator: Validator<ExampleUpdateContent> = ValidatorBuilder.of<ExampleUpdateContent>()
            .konstraint(ExampleUpdateContent::nameJa) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleUpdateContent::nameEn) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleUpdateContent::nameKo) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleUpdateContent::nameZh) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .build()
    }

    override fun validate(): Violations {
        return validator.validate(this).toViolations()
    }
}
