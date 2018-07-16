package jh.multiweather.current.io

import jh.multiweather.current.model.CurrentWeather
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton

@Singleton
expect class CurrentWeatherParser @Inject constructor() {
    fun parse(data: String): CurrentWeather
}