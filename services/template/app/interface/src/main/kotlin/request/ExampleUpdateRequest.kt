package request

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint

data class ExampleUpdateRequest(
    val exampleKey: String,
    val nameJa: String?,
    val nameEn: String?,
    val nameKo: String?,
    val nameZh: String?,
    val enabled: Boolean?
) {
    companion object {
        val validations = ValidatorBuilder.of<ExampleUpdateRequest>()
            .konstraint(ExampleUpdateRequest::exampleKey) {
                greaterThanOrEqual(1).lessThanOrEqual(64)
            }
            .konstraint(ExampleUpdateRequest::nameJa) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleUpdateRequest::nameEn) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleUpdateRequest::nameKo) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .konstraint(ExampleUpdateRequest::nameZh) {
                greaterThanOrEqual(1).lessThanOrEqual(191)
            }
            .build()
    }
}
