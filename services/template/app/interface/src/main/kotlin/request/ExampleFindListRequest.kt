package request

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.konstraint

data class ExampleFindListRequest(
    val limit: Int?,
    val offset: Int?,
    val includeDisabled: Boolean?
) {
    companion object {
        val violations = ValidatorBuilder.of<ExampleFindListRequest>()
            .konstraint(ExampleFindListRequest::limit) {
                greaterThan(0).lessThan(100)
            }
            .konstraint(ExampleFindListRequest::offset) {
                greaterThanOrEqual(0)
            }
            .build()
    }
}
