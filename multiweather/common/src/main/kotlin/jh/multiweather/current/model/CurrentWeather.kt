package jh.multiweather.current.model

import jh.shared.datetime.infrastructure.ZonedDateTime

data class CurrentWeather(
        val timestamp: ZonedDateTime?,
        val location: String?,
        val temperatureCelsius: Double?,
        val pressureMilliBar: Double?,
        val descriptionText: String?,
        val descriptionCode: Int?,
        val windSpeedKmph: Double?,
        val windDirectionDegrees: Double?,
        val sunriseTimestamp: ZonedDateTime?,
        val sunsetTimestamp: ZonedDateTime?
)