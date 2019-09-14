package utility

import java.time.LocalDateTime

/**
 * joda LocalateTimeをjava LocalDatetimeに変換する
 */
internal fun org.joda.time.LocalDateTime.toJavaLocalDateTime(): LocalDateTime {
    return LocalDateTime.of(
        this.year,
        this.monthOfYear,
        this.dayOfMonth,
        this.hourOfDay,
        this.minuteOfHour,
        this.secondOfMinute
    )
}

/**
 * java LocalateTimeをjoda Datetimeに変換する
 */
internal fun LocalDateTime.toJodaDateTime(): org.joda.time.DateTime {
    return org.joda.time.DateTime.parse(this.formatToISO())
}
