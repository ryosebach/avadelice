package service

import result.ExampleCreateResult
import result.ExampleFindListResult
import result.ExampleFindResult
import result.ExampleUpdateResult

interface ExampleService {
    /**
     * ID をもとに Example を1件取得する。
     */
    fun findOneByKey(exampleKey: String): ExampleFindResult

    /**
     * Example 一覧を取得する。
     */
    fun findList(
        limit: Int,
        offset: Int,
        includeDisabled: Boolean
    ): ExampleFindListResult

    /**
     * Example を作成する。
     */
    fun create(
        exampleKey: String,
        nameJa: String,
        nameEn: String,
        nameKo: String,
        nameZh: String,
        enabled: Boolean
    ): ExampleCreateResult
}
