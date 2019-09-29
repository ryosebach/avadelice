package service

import helper.TransactionHelper
import repository.ExampleRepository
import result.ExampleCreateResult
import result.ExampleFindListResult
import result.ExampleFindResult
import result.ExampleUpdateResult
import utility.ServiceDateTime

class ExampleServiceImpl(
    private val transactionHelper: TransactionHelper,
    private val exampleRepository: ExampleRepository
) : ExampleService {
    override fun findOneByKey(exampleKey: String): ExampleFindResult {
        return transactionHelper.slaveTransaction {
            val test = exampleRepository.findOneByKey(exampleKey)
                ?: return@slaveTransaction ExampleFindResult.NotFound
            ExampleFindResult.Success(test)
        }
    }

    override fun findList(
        limit: Int,
        offset: Int,
        includeDisabled: Boolean
    ): ExampleFindListResult {
        val enabled = if (!includeDisabled) true else null
        return transactionHelper.slaveTransaction {
            val testList = exampleRepository.findList(
                limit = limit,
                offset = offset,
                enabled = enabled
            )
            ExampleFindListResult.Success(testList)
        }
    }

    override fun create(
        exampleKey: String,
        nameJa: String,
        nameEn: String,
        nameKo: String,
        nameZh: String,
        enabled: Boolean
    ): ExampleCreateResult {
        val now = ServiceDateTime.now()
        return transactionHelper.masterTransaction {
            val testId = exampleRepository.create(
                exampleKey = exampleKey,
                nameJa = nameJa,
                nameEn = nameEn,
                nameKo = nameKo,
                nameZh = nameZh,
                enabled = enabled,
                createdAt = now,
                updatedAt = now
            )
            val test = exampleRepository.findOneById(testId)
                ?: return@masterTransaction ExampleCreateResult.BadRequest
            ExampleCreateResult.Success(test)
        }
    }

    override fun update(
        exampleKey: String,
        nameJa: String?,
        nameEn: String?,
        nameKo: String?,
        nameZh: String?,
        enabled: Boolean?
    ): ExampleUpdateResult {
        val now = ServiceDateTime.now()
        return transactionHelper.masterTransaction {
            val test = exampleRepository.findOneByKey(exampleKey)
                ?: return@masterTransaction ExampleUpdateResult.BadRequest
            val testUpdateResult = exampleRepository.updateById(
                id = test.id,
                nameJa = nameJa,
                nameEn = nameEn,
                nameKo = nameKo,
                nameZh = nameZh,
                enabled = enabled,
                updatedAt = now
            )
            if (!testUpdateResult) {
                return@masterTransaction ExampleUpdateResult.BadRequest
            }
            val updatedTest = exampleRepository.findOneByKey(exampleKey)
                ?: return@masterTransaction ExampleUpdateResult.BadRequest
            ExampleUpdateResult.Success(updatedTest)
        }
    }
}
