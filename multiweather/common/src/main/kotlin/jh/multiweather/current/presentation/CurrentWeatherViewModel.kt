package jh.multiweather.current.presentation

import jh.multiweather.current.model.CurrentWeatherFormatted
import jh.multiweather.current.model.CurrentWeatherState
import jh.multiweather.current.platform.CurrentWeatherController
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
class CurrentWeatherViewModel @Inject constructor(
        private val currentWeatherController: CurrentWeatherController
) {
    companion object {
        private const val API_KEY = "060babdcb0097cb661c39c2c9e6c4a09"
    }

    private val statesSubject = createBehaviorSubject(CurrentWeatherState())

    val states: Observable<CurrentWeatherState> = statesSubject.hide()

    fun refresh() {
        statesSubject.onNext(CurrentWeatherState())

        currentWeatherController.load(API_KEY, "Brno")
                .map {
                    CurrentWeatherFormatted(
                            it.timestamp?.format(dateTimeFormatterOfPattern("dd. LLLL H:mm")),
                            it.location,
                            it.temperatureCelsius?.let { "${it.roundToInt()} °C" },
                            it.pressureMilliBar?.let { "${it.roundToInt()} mBar" },
                            it.descriptionText?.capitalize(),
                            it.descriptionCode?.toWeatherDescription() ?: UNKNOWN,
                            it.windSpeedKmph?.let { "$it km/h" },
                            it.windDirectionDegrees?.let { "${it.roundToInt()}°" },
                            it.sunriseTimestamp?.format(dateTimeFormatterOfPattern("H:mm")),
                            it.sunsetTimestamp?.format(dateTimeFormatterOfPattern("H:mm"))
                    )
                }
                // TODO observe on mainThread when AndroidSchedulers are available in android module
                .subscribe({
                    statesSubject.onNext(CurrentWeatherState(
                            currentWeather = it,
                            isCurrentWeatherVisible = true,
                            isLoadingVisible = false
                    ))
                }, {
                    statesSubject.onNext(CurrentWeatherState(
                            isLoadingVisible = false,
                            errorMessage = it.toString(),
                            isErrorMessageVisible = true
                    ))
                })
    }
}

