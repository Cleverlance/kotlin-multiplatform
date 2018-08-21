package jh.multiweather.current.platform

import jh.multiweather.current.io.CurrentWeatherParser
import jh.multiweather.current.io.CurrentWeatherService
import jh.multiweather.current.model.CurrentWeather
import jh.multiweather.shared.io.WeatherServiceParams
import jh.multiweather.shared.model.toLanguage
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.listeners.infrastructure.MutableObservable
import jh.shared.listeners.infrastructure.Observable
import jh.shared.locale.platform.LocaleController

@Singleton
class CurrentWeatherController @Inject constructor(
        private val currentWeatherService: CurrentWeatherService,
        private val currentWeatherParser: CurrentWeatherParser,
        private val localeController: LocaleController
) {
    private val dataObservable = MutableObservable<CurrentWeather>()

    fun load(apiKey: String, city: String): Observable<CurrentWeather> {
        currentWeatherService.load(
                apiKey,
                city,
                localeController.getCurrentLocale().toLanguage().apiCode,
                WeatherServiceParams.METRIC_UNITS
        )
                .subscribe { dataObservable.post(currentWeatherParser.parse(it)) }

        return dataObservable.observable
    }
}