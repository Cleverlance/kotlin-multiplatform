package jh.multiweather.forecast.io

import jh.multiweather.forecast.model.ForecastWeather
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton

@Singleton
expect class ForecastWeatherParser @Inject constructor() {
    fun parse(data: String): List<ForecastWeather>
}