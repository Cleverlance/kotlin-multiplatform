package jh.multiweather.forecast.model

import jh.multiweather.shared.model.WeatherDescription

sealed class ForecastWeatherFormatted {
    data class Item(
            val timestamp: String?,
            val location: String?,
            val temperatureMin: String?,
            val temperatureMax: String?,
            val descriptionText: String?,
            val descriptionIcon: WeatherDescription
    ) : ForecastWeatherFormatted()

    data class Header(val header: String) : ForecastWeatherFormatted()
}