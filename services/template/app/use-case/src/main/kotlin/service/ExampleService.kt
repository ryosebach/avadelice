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
}
