package request

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint

data class ExampleCreateRequest(
    val exampleKey: String,
    val nameJa: String,
    val nameEn: String,
    val nameKo: String,
    val nameZh: String,
    val enabled: Boolean
) {
    companion object {
        val validations = ValidatorBuilder.of<ExampleCreateRequest>()
            .konstraint(ExampleCreateRequest::exampleKey) {
                greaterThanOrEqual(1).lessThanOrEqual(64)
            }
            .konstraint(ExampleCreateRequest::nameJa) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleCreateRequest::nameEn) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleCreateRequest::nameKo) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleCreateRequest::nameZh) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .build()
    }
}
