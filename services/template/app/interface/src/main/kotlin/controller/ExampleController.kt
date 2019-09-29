package controller

import entity.Example
import request.ExampleCreateRequest
import request.ExampleFindListRequest
import request.ExampleFindRequest
import request.ExampleUpdateRequest
import response.ExampleCreateResponse
import response.ExampleFindListResponse
import response.ExampleFindResponse
import response.ExampleUpdateResponse
import response.error.Error
import response.resource.ExampleResource
import result.ExampleCreateResult
import result.ExampleFindListResult
import result.ExampleFindResult
import result.ExampleUpdateResult
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

    fun findList(request: ExampleFindListRequest): ExampleFindListResponse {
        val validation = ExampleFindListRequest.violations.validate(request)
        if (!validation.isValid) {
            return ExampleFindListResponse.BadRequest(Error(validation.map { it.message() }.toString()))
        }
        return when (val result = exampleService.findList(
            limit = request.limit ?: 10,
            offset = request.offset ?: 0,
            includeDisabled = request.includeDisabled ?: false
        )) {
            is ExampleFindListResult.Success ->
                ExampleFindListResponse.Success(result.exampleList.map { it.toResource() })
            is ExampleFindListResult.NotFound ->
                ExampleFindListResponse.NotFound(Error("example are not found."))
        }
    }

    fun create(request: ExampleCreateRequest): ExampleCreateResponse {
        val validation = ExampleCreateRequest.validations.validate(request)
        if (!validation.isValid) {
            return ExampleCreateResponse.BadRequest(Error(validation.map { it.message() }.toString()))
        }
        return when (val result = exampleService.create(
            exampleKey = request.exampleKey,
            nameJa = request.nameJa,
            nameEn = request.nameEn,
            nameKo = request.nameKo,
            nameZh = request.nameZh,
            enabled = request.enabled
        )) {
            is ExampleCreateResult.Success ->
                ExampleCreateResponse.Success(result.example.toResource())
            is ExampleCreateResult.BadRequest ->
                ExampleCreateResponse.BadRequest(Error("Creating example is failed"))
        }
    }

    fun update(request: ExampleUpdateRequest): ExampleUpdateResponse {
        val validation = ExampleUpdateRequest.validations.validate(request)
        if (!validation.isValid) {
            return ExampleUpdateResponse.BadRequest(Error(validation.map { it.message() }.toString()))
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
                ExampleUpdateResponse.BadRequest(Error("Updating example is failed"))
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
