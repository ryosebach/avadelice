package controller

import adapter.Controller
import adapter.Request
import adapter.Response
import entity.Example
import request.ExampleUpdateContent
import request.ExampleUpdateParams
import resource.ErrorResource
import resource.ErrorType
import resource.ExampleResource
import result.ExampleUpdateResult
import service.ExampleService
import utility.formatToISO

class ExampleUpdateController(
    private val exampleService: ExampleService
) : Controller<ExampleUpdateParams, ExampleUpdateContent, ExampleResource> {
    override fun proceed(request: Request<ExampleUpdateParams, ExampleUpdateContent>): Response<ExampleResource> {
        val content = request.content!!
        val params = request.params!!
        return when (val result = exampleService.update(
            exampleKey = params.exampleKey,
            nameJa = content.nameJa,
            nameEn = content.nameEn,
            nameKo = content.nameKo,
            nameZh = content.nameZh,
            enabled = content.enabled
        )) {
            is ExampleUpdateResult.Success -> Response.Success(
                result.example.toResource()
            )
            is ExampleUpdateResult.BadRequest -> Response.Failure(
                ErrorResource(ErrorType.SYSTEM_ERROR, "Failed Update.")
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
