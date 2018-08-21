package jh.multiweather.current.io

import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.listeners.infrastructure.Observable

@Singleton
expect class CurrentWeatherService @Inject constructor() {
    fun load(apiKey: String, city: String, language: String, units: String): Observable<String>
}