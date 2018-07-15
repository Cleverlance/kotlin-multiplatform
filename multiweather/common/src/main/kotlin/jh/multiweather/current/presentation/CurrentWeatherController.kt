package jh.multiweather.current.presentation

import jh.multiweather.current.io.CurrentWeatherParser
import jh.multiweather.current.io.CurrentWeatherService
import jh.multiweather.current.io.CurrentWeatherServiceParams
import jh.multiweather.current.model.CurrentWeather
import jh.multiweather.shared.infrastructure.*

@Singleton
class CurrentWeatherController @Inject constructor(
        private val currentWeatherService: CurrentWeatherService,
        private val currentWeatherParser: CurrentWeatherParser
) {
    companion object {
        private const val API_KEY = "060babdcb0097cb661c39c2c9e6c4a09"
    }

    fun load(city: String): Single<CurrentWeather> = currentWeatherService.load(API_KEY, city, CurrentWeatherServiceParams.METRIC_UNITS)
            .observeOn(Schedulers.computation())
            .map { currentWeatherParser.parse(it) }
}