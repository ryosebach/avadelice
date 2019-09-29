package repository

import entity.Example
import java.time.LocalDateTime

interface ExampleRepository {
    fun findOneByKey(exampleKey: String): Example?
}
