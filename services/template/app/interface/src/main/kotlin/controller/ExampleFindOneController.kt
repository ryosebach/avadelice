package controller

import adapter.Controller
import adapter.NoInput
import adapter.Request
import adapter.Response
import extension.toResource
import request.ExampleFindOneParams
import resource.ErrorResource
import resource.ErrorType
import resource.ExampleResource
import result.ExampleFindResult
import service.ExampleService

class ExampleFindOneController(
    private val exampleService: ExampleService
) : Controller<ExampleFindOneParams, NoInput, ExampleResource> {
    override fun proceed(request: Request<ExampleFindOneParams, NoInput>): Response<ExampleResource> {
        val params = request.params!!
        return when (val result = exampleService.findOneByKey(params.exampleKey)) {
            is ExampleFindResult.Success -> Response.Success(
                result.example.toResource()
            )
            is ExampleFindResult.NotFound -> Response.Failure(
                ErrorResource(ErrorType.NOT_FOUND, "Example Not Found.")
            )
        }
    }
}
