package jh.shared.datetime.infrastructure

import platform.Foundation.*

actual typealias ZonedDateTime = NSDate

actual fun ZonedDateTime.format(formatter: DateTimeFormatter) = formatter.stringFromDate(this)

actual fun ZonedDateTime.toLocalDate() = this

actual fun Long.toZonedDateTime() = NSDate.timeIntervalSince1970(this)
