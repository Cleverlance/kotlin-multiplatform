package jh.multiweather.current.platform

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.computation
import jh.multiweather.current.io.CurrentWeatherParser
import jh.multiweather.current.io.CurrentWeatherService
import jh.multiweather.current.model.CurrentWeather
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentWeatherController @Inject constructor(
        private val currentWeatherService: CurrentWeatherService,
        private val currentWeatherParser: CurrentWeatherParser
) {
    companion object {
        private const val API_KEY = "060babdcb0097cb661c39c2c9e6c4a09"
    }

    fun load(city: String): Single<CurrentWeather> {
        Timber.d("Refresh")

        return currentWeatherService.load(API_KEY, city, CurrentWeatherService.METRIC_UNITS)
                .observeOn(computation())
                .map { currentWeatherParser.parse(it) }
    }
}