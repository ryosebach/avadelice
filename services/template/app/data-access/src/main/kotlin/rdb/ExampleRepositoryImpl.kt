package rdb

import entity.Example
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import rdb.table.ExampleTable
import repository.ExampleRepository
import utility.toJavaLocalDateTime

class ExampleRepositoryImpl : ExampleRepository {
    override fun findOneByKey(exampleKey: String): Example? {
        val campaign = ExampleTable
            .select { ExampleTable.exampleKey eq exampleKey }
            .limit(1)
            .singleOrNull()

        return campaign?.toEntity()
    }

    /**
     * エンティティに変換する
     */
    private fun ResultRow.toEntity(): Example {
        return Example(
            this[ExampleTable.id].value,
            this[ExampleTable.exampleKey],
            this[ExampleTable.nameJa],
            this[ExampleTable.nameEn],
            this[ExampleTable.nameKo],
            this[ExampleTable.nameZh],
            this[ExampleTable.enabled],
            this[ExampleTable.createdAt].toLocalDateTime().toJavaLocalDateTime(),
            this[ExampleTable.updatedAt].toLocalDateTime().toJavaLocalDateTime()
        )
    }
}
