package jh.shared.datetime.infrastructure

expect class DateTimeFormatter

// TODO how to expect Java static methods like DateTimeFormatter.ofPattern(...)?
expect fun dateTimeFormatterOfPattern(pattern: String): DateTimeFormatter