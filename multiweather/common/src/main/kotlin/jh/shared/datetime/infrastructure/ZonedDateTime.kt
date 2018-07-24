package jh.shared.datetime.infrastructure

expect class ZonedDateTime

expect fun Long.toZonedDateTime(): ZonedDateTime

expect fun ZoneDateTime.format(formatter: DateTimeFormatter): String

expect fun ZoneDateTime.toLocalDate(): LocalDate