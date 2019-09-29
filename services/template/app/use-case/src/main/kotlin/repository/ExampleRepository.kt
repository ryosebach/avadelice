package repository

import entity.Example
import java.time.LocalDateTime

interface ExampleRepository {
    fun findOneById(id: Long): Example?

    fun findOneByKey(exampleKey: String): Example?

    fun findList(
        limit: Int,
        offset: Int,
        enabled: Boolean?
    ): List<Example>
}
