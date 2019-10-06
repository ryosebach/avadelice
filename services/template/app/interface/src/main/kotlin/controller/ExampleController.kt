package controller

import entity.Example
import request.ExampleUpdateRequest
import resource.ErrorResource
import resource.ErrorType
import response.ExampleUpdateResponse
import resource.ExampleResource
import result.ExampleUpdateResult
import service.ExampleService
import utility.formatToISO

class ExampleController(
    private val exampleService: ExampleService
) {
    fun update(request: ExampleUpdateRequest): ExampleUpdateResponse {
        val validation = ExampleUpdateRequest.validations.validate(request)
        if (!validation.isValid) {
            return ExampleUpdateResponse.BadRequest(ErrorResource(ErrorType.VALIDATION_ERROR, validation.map { it.message() }.toString()))
        }
        return when (val result = exampleService.update(
            exampleKey = request.exampleKey,
            nameJa = request.nameJa,
            nameEn = request.nameEn,
            nameKo = request.nameKo,
            nameZh = request.nameZh,
            enabled = request.enabled
        )) {
            is ExampleUpdateResult.Success ->
                ExampleUpdateResponse.Success(result.example.toResource())
            is ExampleUpdateResult.BadRequest ->
                ExampleUpdateResponse.BadRequest(ErrorResource(ErrorType.SYSTEM_ERROR, "Updating example is failed"))
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
