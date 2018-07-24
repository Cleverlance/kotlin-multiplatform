package jh.shared.datetime.infrastructure

import platform.Foundation.*
import kotlin.*

actual typealias DateTimeFormatter = NSDateFormatter

actual fun dateTimeFormatterOfPattern(pattern: String): DateTimeFormatter {
    val formatter = NSDateFormatter()
    formatter.dateFormat = pattern
    return formatter
}
