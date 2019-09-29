package service

import helper.TransactionHelper
import repository.ExampleRepository
import result.ExampleFindListResult
import result.ExampleFindResult

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
}
