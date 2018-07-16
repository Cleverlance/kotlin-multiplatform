package jh.shared.datetime.infrastructure

import org.threeten.bp.format.DateTimeFormatter

actual typealias DateTimeFormatter = DateTimeFormatter

actual fun dateTimeFormatterOfPattern(pattern: String): DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)