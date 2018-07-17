package jh.multiweather.forecast.io

import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.rx.infrastructure.Single

@Singleton
expect class ForecastWeatherService @Inject constructor() {
    fun load(apiKey: String, city: String, language: String, units: String): Single<String>
}