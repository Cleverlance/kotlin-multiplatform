package jh.multiweather.forecast.presentation

import jh.multiweather.forecast.model.ForecastWeatherFormatted
import jh.multiweather.forecast.model.ForecastWeatherState
import jh.multiweather.forecast.platform.ForecastWeatherController
import jh.multiweather.shared.model.WeatherDescription.UNKNOWN
import jh.multiweather.shared.model.toWeatherDescription
import jh.shared.datetime.infrastructure.dateTimeFormatterOfPattern
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.rx.infrastructure.Observable
import jh.shared.rx.infrastructure.createBehaviorSubject
import jh.shared.rx.infrastructure.map
import jh.shared.rx.infrastructure.subscribe
import kotlin.math.roundToInt

@Singleton
class ForecastWeatherViewModel @Inject constructor(
        private val forecastWeatherController: ForecastWeatherController
) {
    companion object {
        private const val API_KEY = "060babdcb0097cb661c39c2c9e6c4a09"
    }

    private val statesSubject = createBehaviorSubject(ForecastWeatherState())

    val states: Observable<ForecastWeatherState> = statesSubject.hide()

    fun refresh() {
        statesSubject.onNext(ForecastWeatherState())

        forecastWeatherController.load(API_KEY, "Brno")
                .map {
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
                                                        it.temperatureMinCelsius?.let { "${it.roundToInt()}°" },
                                                        it.temperatureMaxCelsius?.let { "${it.roundToInt()}°" },
                                                        it.descriptionText,
                                                        it.descriptionCode?.toWeatherDescription() ?: UNKNOWN
                                                )
                                            })
                                        }
                            }
                }
                // TODO observe on mainThread when AndroidSchedulers are available in android module
                .subscribe({
                    statesSubject.onNext(ForecastWeatherState(
                            forecastWeathers = it,
                            isForecastWeatherVisible = true,
                            isLoadingVisible = false
                    ))
                }, {
                    statesSubject.onNext(ForecastWeatherState(
                            isLoadingVisible = false,
                            errorMessage = it.toString(),
                            isErrorMessageVisible = true
                    ))
                })
    }
}