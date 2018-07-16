package jh.multiweather.current.io

import jh.multiweather.shared.infrastructure.Inject
import jh.multiweather.shared.infrastructure.Singleton
import jh.shared.rx.infrastructure.Single

@Singleton
expect class CurrentWeatherService @Inject constructor() {
    fun load(apiKey: String, city: String, units: String): Single<String>
}