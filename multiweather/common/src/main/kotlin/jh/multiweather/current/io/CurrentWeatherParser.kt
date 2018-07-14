package jh.multiweather.current.io

import jh.multiweather.current.model.CurrentWeather
import jh.multiweather.shared.infrastructure.Inject
import jh.multiweather.shared.infrastructure.Singleton

@Singleton
expect class CurrentWeatherParser @Inject constructor() {
    fun parse(data: String): CurrentWeather
}