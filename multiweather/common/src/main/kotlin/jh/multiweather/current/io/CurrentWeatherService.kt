package jh.multiweather.current.io

import jh.multiweather.shared.infrastructure.Inject
import jh.multiweather.shared.infrastructure.Single
import jh.multiweather.shared.infrastructure.Singleton

@Singleton
expect class CurrentWeatherService @Inject constructor() {
    fun load(apiKey: String, city: String, units: String): Single<String>
}