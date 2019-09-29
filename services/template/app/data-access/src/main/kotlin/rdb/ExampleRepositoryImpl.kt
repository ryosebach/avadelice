package rdb

import entity.Example
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import rdb.table.ExampleTable
import repository.ExampleRepository
import utility.toJavaLocalDateTime
import utility.toJodaDateTime
import java.time.LocalDateTime

class ExampleRepositoryImpl : ExampleRepository {

    override fun findOneById(id: Long): Example? {
        val campaign = ExampleTable
            .select { ExampleTable.id eq id }
            .limit(1)
            .singleOrNull()

        return campaign?.toEntity()
    }

    override fun findOneByKey(exampleKey: String): Example? {
        val campaign = ExampleTable
            .select { ExampleTable.exampleKey eq exampleKey }
            .limit(1)
            .singleOrNull()

        return campaign?.toEntity()
    }

    override fun findList(
        limit: Int,
        offset: Int,
        enabled: Boolean?
    ): List<Example> {
        val campaignQuery = ExampleTable
            .select { Op.build { Op.TRUE } }
        enabled?.let {
            campaignQuery.andWhere { ExampleTable.enabled eq it }
        }
        campaignQuery
            .limit(limit, offset)
            .orderBy(ExampleTable.id, SortOrder.ASC)

        return campaignQuery.map { it.toEntity() }
    }

    override fun create(
        exampleKey: String,
        nameJa: String,
        nameEn: String,
        nameKo: String,
        nameZh: String,
        enabled: Boolean,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime
    ): Long {
        val campaignId = ExampleTable.insertAndGetId {
            it[this.exampleKey] = exampleKey
            it[this.nameJa] = nameJa
            it[this.nameEn] = nameEn
            it[this.nameKo] = nameKo
            it[this.nameZh] = nameZh
            it[this.enabled] = enabled
            it[this.createdAt] = createdAt.toJodaDateTime()
            it[this.updatedAt] = updatedAt.toJodaDateTime()
        }

        return campaignId.value
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
