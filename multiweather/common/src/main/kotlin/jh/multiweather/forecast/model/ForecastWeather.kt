package jh.multiweather.forecast.model

import jh.shared.datetime.infrastructure.ZonedDateTime

data class ForecastWeather(
        val timestamp: ZonedDateTime?,
        val location: String?,
        val temperatureMinCelsius: Double?,
        val temperatureMaxCelsius: Double?,
        val descriptionText: String?,
        val descriptionCode: Int?
)