package utility

import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

/**
 * OffsetDateTimeをISO 8601形式の文字列に変換する
 */
fun OffsetDateTime.formatToISO(): String {
    return this.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

/**
 * OffsetDateTimeをLocalDateTimeに変換する
 */

/**
 * LocalDateTimeをOffsetDateTimeに変換する
 */
fun LocalDateTime.toOffsetDateTime(): OffsetDateTime {
    return OffsetDateTime.of(this, ServiceDateTime.zoneOffset)
}

/**
 * LocalDateTimeをオフセット付きのISO 8601形式の文字列に変換する
 */
fun LocalDateTime.formatToISO(): String {
    return this.toOffsetDateTime().formatToISO()
}

/**
 * 文字列を LocalDateTime に変換する
 */
fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}

/**
 * Stringを OffsetDateTime に変換する
 */
fun String.toOffsetDateTime(): OffsetDateTime {
    return OffsetDateTime.parse(this, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
}
