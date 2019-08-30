package utility

import java.time.Clock
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime

/**
 * Service内で扱われる時刻を扱うクラス
 */
class ServiceDateTime {
    companion object {
        private var clock: Clock? = null
        private val zoneId = Clock.systemDefaultZone().zone
        internal val zoneOffset = OffsetDateTime.now(zoneId).offset

        /**
         * 現在時刻をLocalDateTimeで取得する
         */
        fun now(): LocalDateTime {
            return clock?.let { LocalDateTime.now(it) }
                ?: LocalDateTime.now(zoneId)
        }

        /**
         * 現在時刻を固定する
         */
        fun fixClock(localDateTime: LocalDateTime) {
            this.clock = Clock.fixed(localDateTime.atZone(zoneId).toInstant(), zoneId)
        }

        /**
         * OffsetDateTimeをLocalDateTimeに変換する
         */
        fun from(offsetDateTime: OffsetDateTime): LocalDateTime {
            val zonedDateTime = ZonedDateTime.ofInstant(offsetDateTime.toInstant(), zoneId)
            return zonedDateTime.toLocalDateTime()
        }
    }
}
