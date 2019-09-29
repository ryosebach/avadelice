package controller

import entity.Example
import request.ExampleFindRequest
import response.ExampleFindResponse
import response.error.Error
import response.resource.ExampleResource
import result.ExampleFindResult
import service.ExampleService
import utility.formatToISO

class ExampleController(
    private val exampleService: ExampleService
) {
    fun one(request: ExampleFindRequest): ExampleFindResponse {
        return when (val result = exampleService.findOneByKey(request.exampleKey)) {
            is ExampleFindResult.Success ->
                ExampleFindResponse.Success(result.example.toResource())
            is ExampleFindResult.NotFound ->
                ExampleFindResponse.NotFound(Error("example is not found."))
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
