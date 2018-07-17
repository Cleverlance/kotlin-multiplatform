package jh.multiweather.current.platform

import jh.multiweather.current.io.CurrentWeatherParser
import jh.multiweather.current.io.CurrentWeatherService
import jh.multiweather.current.model.CurrentWeather
import jh.multiweather.shared.io.WeatherServiceParams
import jh.multiweather.shared.model.toLanguage
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.locale.platform.LocaleController
import jh.shared.rx.infrastructure.Schedulers
import jh.shared.rx.infrastructure.Single
import jh.shared.rx.infrastructure.map

@Singleton
class CurrentWeatherController @Inject constructor(
        private val currentWeatherService: CurrentWeatherService,
        private val currentWeatherParser: CurrentWeatherParser,
        private val localeController: LocaleController
) {
    fun load(apiKey: String, city: String): Single<CurrentWeather> = currentWeatherService.load(
            apiKey,
            city,
            localeController.getCurrentLocale().toLanguage().apiCode,
            WeatherServiceParams.METRIC_UNITS
    )
            .observeOn(Schedulers.computation())
            .map { currentWeatherParser.parse(it) }
}