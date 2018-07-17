package jh.shared.datetime.infrastructure

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

actual typealias ZonedDateTime = ZonedDateTime

actual fun Long.toZonedDateTime(): ZonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())