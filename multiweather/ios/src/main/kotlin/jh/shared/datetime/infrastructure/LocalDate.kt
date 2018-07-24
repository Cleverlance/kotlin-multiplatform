package jh.shared.datetime.infrastructure

import platform.Foundation.*

actual typealias LocalDate = NSDate

fun LocalDate.format(formatter: DateTimeFormatter): String = formatter.stringFromDate(this)
