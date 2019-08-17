package entity

import java.time.LocalDateTime

data class Example(
    val id: Long,
    val testKey: String,
    val nameJa: String,
    val nameEn: String,
    val nameKo: String,
    val nameZh: String,
    val enabled: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
