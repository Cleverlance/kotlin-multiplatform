package jh.multiweather.current.model

expect class DateTimeFormatter

// TODO how to expect Java static methods like DateTimeFormatter.ofPattern(...)?
expect fun dateTimeFormatterOfPattern(pattern: String): DateTimeFormatter