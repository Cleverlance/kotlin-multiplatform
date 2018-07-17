package jh.multiweather.current.model

import jh.multiweather.shared.model.WeatherDescription

data class CurrentWeatherFormatted(
        val timestamp: String?,
        val location: String?,
        val temperature: String?,
        val pressure: String?,
        val descriptionShort: String?,
        val descriptionLong: String?,
        val descriptionIcon: WeatherDescription,
        val windSpeed: String?,
        val windDirection: String?,
        val sunriseTimestamp: String?,
        val sunsetTimestamp: String?
)