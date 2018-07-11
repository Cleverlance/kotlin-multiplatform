package jh.multiweather.current.model

import java.time.ZonedDateTime

data class CurrentWeather(
        val date: ZonedDateTime,
        val temperatureCelsius: Double,
        val description: String
)