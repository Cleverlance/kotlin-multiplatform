package jh.multiweather.forecast.platform

import jh.multiweather.forecast.io.ForecastWeatherParser
import jh.multiweather.forecast.io.ForecastWeatherService
import jh.multiweather.forecast.model.ForecastWeather
import jh.multiweather.shared.io.WeatherServiceParams
import jh.multiweather.shared.model.toLanguage
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.locale.platform.LocaleController
import jh.shared.rx.infrastructure.Schedulers
import jh.shared.rx.infrastructure.Single
import jh.shared.rx.infrastructure.map

@Singleton
class ForecastWeatherController @Inject constructor(
        private val forecastWeatherService: ForecastWeatherService,
        private val forecastWeatherParser: ForecastWeatherParser,
        private val localeController: LocaleController
) {
    fun load(apiKey: String, city: String): Single<List<ForecastWeather>> = forecastWeatherService.load(
            apiKey,
            city,
            localeController.getCurrentLocale().toLanguage().apiCode,
            WeatherServiceParams.METRIC_UNITS
    )
            .observeOn(Schedulers.computation())
            .map { forecastWeatherParser.parse(it) }
}