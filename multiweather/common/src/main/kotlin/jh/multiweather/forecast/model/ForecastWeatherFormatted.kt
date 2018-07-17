package jh.multiweather.forecast.model

import jh.multiweather.shared.model.WeatherDescription

data class ForecastWeatherFormatted(
        val timestamp: String?,
        val location: String?,
        val temperatureMin: String?,
        val temperatureMax: String?,
        val descriptionText: String?,
        val descriptionIcon: WeatherDescription
)