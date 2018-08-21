package jh.multiweather.forecast.presentation

import jh.multiweather.forecast.model.ForecastWeatherFormatted
import jh.multiweather.forecast.model.ForecastWeatherState
import jh.multiweather.forecast.platform.ForecastWeatherController
import jh.multiweather.shared.model.WeatherDescription.UNKNOWN
import jh.multiweather.shared.model.toWeatherDescription
import jh.multiweather.shared.platform.InputController
import jh.shared.datetime.infrastructure.dateTimeFormatterOfPattern
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.listeners.infrastructure.MutableObservable
import jh.shared.listeners.infrastructure.Observable
import kotlin.math.roundToInt

@Singleton
class ForecastWeatherViewModel @Inject constructor(
        private val forecastWeatherController: ForecastWeatherController,
        private val inputController: InputController
) {
    companion object {
        private const val API_KEY = "060babdcb0097cb661c39c2c9e6c4a09"
    }

    private val statesObservable = MutableObservable(notifyLastEmissionOnSubscription = true, defaultValue = ForecastWeatherState())

    val states: Observable<ForecastWeatherState> = statesObservable.observable

    fun refresh() {
        statesObservable.post(ForecastWeatherState(isLoadingVisible = true))

        forecastWeatherController.load(API_KEY, inputController.city)
                .subscribe {
                    it
                            .filter { it.timestamp != null }
                            .groupBy { it.timestamp!!.toLocalDate() }
                            .flatMap {
                                mutableListOf<ForecastWeatherFormatted>(ForecastWeatherFormatted.Header(it.key.format(dateTimeFormatterOfPattern("EEEE dd. LLLL"))))
                                        .apply {
                                            addAll(it.value.map {
                                                ForecastWeatherFormatted.Item(
                                                        it.timestamp?.format(dateTimeFormatterOfPattern("H:mm")),
                                                        it.location,
                                                        it.temperatureMinCelsius?.let { "${it.roundToInt()} °C" },
                                                        it.temperatureMaxCelsius?.let { "${it.roundToInt()} °C" },
                                                        it.descriptionText,
                                                        it.descriptionCode?.toWeatherDescription() ?: UNKNOWN
                                                )
                                            })
                                        }
                            }
                            .let {
                                statesObservable.post(ForecastWeatherState(
                                        forecastWeathers = it,
                                        isForecastWeatherVisible = true,
                                        isLoadingVisible = false
                                ))
                            }
                }
    }
}