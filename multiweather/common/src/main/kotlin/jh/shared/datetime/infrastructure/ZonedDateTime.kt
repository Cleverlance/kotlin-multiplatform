package jh.shared.datetime.infrastructure

expect class ZonedDateTime {
    // TODO use DateTimeFormatter only if format strings are compatible across platforms; otherwise create one formatting method for every format needed
    fun format(formatter: DateTimeFormatter): String
}