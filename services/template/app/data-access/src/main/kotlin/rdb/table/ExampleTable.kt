package rdb.table

import org.jetbrains.exposed.dao.LongIdTable
import org.jetbrains.exposed.sql.datetime

object ExampleTable : LongIdTable("example") {
    val exampleKey = varchar("example_key", 191)
    val nameJa = varchar("name_ja", 191)
    val nameEn = varchar("name_en", 191)
    val nameKo = varchar("name_ko", 191)
    val nameZh = varchar("name_zh", 191)
    val enabled = bool("enabled")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
