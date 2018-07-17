package jh.multiweather.current.io

import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.rx.infrastructure.Single

@Singleton
expect class CurrentWeatherService @Inject constructor() {
    fun load(apiKey: String, city: String, language: String, units: String): Single<String>
}