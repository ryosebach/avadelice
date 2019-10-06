package controller

import adapter.Controller
import adapter.NoInput
import adapter.Request
import adapter.Response
import extension.toResource
import request.ExampleFindListParams
import resource.ExampleResource
import result.ExampleFindListResult
import service.ExampleService

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
}
