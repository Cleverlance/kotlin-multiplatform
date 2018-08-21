package jh.multiweather.forecast.platform

import jh.multiweather.forecast.io.ForecastWeatherParser
import jh.multiweather.forecast.io.ForecastWeatherService
import jh.multiweather.forecast.model.ForecastWeather
import jh.multiweather.shared.io.WeatherServiceParams
import jh.multiweather.shared.model.toLanguage
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.listeners.infrastructure.MutableObservable
import jh.shared.listeners.infrastructure.Observable
import jh.shared.locale.platform.LocaleController

@Singleton
class ForecastWeatherController @Inject constructor(
        private val forecastWeatherService: ForecastWeatherService,
        private val forecastWeatherParser: ForecastWeatherParser,
        private val localeController: LocaleController
) {
    private val dataObservable = MutableObservable<List<ForecastWeather>>()

    fun load(apiKey: String, city: String): Observable<List<ForecastWeather>> {
        forecastWeatherService.load(
                apiKey,
                city,
                localeController.getCurrentLocale().toLanguage().apiCode,
                WeatherServiceParams.METRIC_UNITS
        )
                .subscribe { dataObservable.post(forecastWeatherParser.parse(it)) }

        return dataObservable.observable
    }
}