package controller

import adapter.Controller
import adapter.NoInput
import adapter.Request
import adapter.Response
import extension.toResource
import request.ExampleCreateContent
import resource.ErrorResource
import resource.ErrorResource.ErrorType
import resource.ExampleResource
import result.ExampleCreateResult
import service.ExampleService

class ExampleCreateController(
    private val exampleService: ExampleService
) : Controller<NoInput, ExampleCreateContent, ExampleResource> {
    override fun proceed(request: Request<NoInput, ExampleCreateContent>): Response<ExampleResource> {
        val content = request.content!!
        return when (val result = exampleService.create(
            exampleKey = content.exampleKey,
            nameJa = content.nameJa,
            nameEn = content.nameEn,
            nameKo = content.nameKo,
            nameZh = content.nameZh,
            enabled = content.enabled
        )) {
            is ExampleCreateResult.Success -> Response.Success(
                result.example.toResource()
            )
            is ExampleCreateResult.BadRequest -> Response.Failure(
                ErrorResource(ErrorType.SYSTEM_ERROR, "Failed Create.")
            )
        }
    }
}
