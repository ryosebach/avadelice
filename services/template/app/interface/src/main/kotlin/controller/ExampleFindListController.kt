package controller

import adapter.Controller
import adapter.NoInput
import adapter.Request
import adapter.Response
import entity.Example
import request.ExampleFindListParams
import resource.ExampleResource
import result.ExampleFindListResult
import service.ExampleService
import utility.formatToISO

class ExampleFindListController(
    private val exampleService: ExampleService
) : Controller<ExampleFindListParams, NoInput, List<ExampleResource>> {
    override fun proceed(request: Request<ExampleFindListParams, NoInput>): Response<List<ExampleResource>> {
        val params = request.params!!
        return when (val result = exampleService.findList(
            params.limit ?: 10,
            params.offset ?: 0,
            params.includeDisabled ?: false
        )) {
            is ExampleFindListResult.Success -> Response.Success(
                result.exampleList.map { it.toResource() }
            )
        }
    }

    /**
     * エンティティをリソースの形式に変換する
     */
    private fun Example.toResource(): ExampleResource {
        return ExampleResource(
            this.testKey,
            this.nameJa,
            this.nameEn,
            this.nameKo,
            this.nameZh,
            this.enabled,
            this.createdAt.formatToISO(),
            this.updatedAt.formatToISO()
        )
    }
}
