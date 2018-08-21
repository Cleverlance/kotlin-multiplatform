package jh.multiweather.current.presentation

import jh.multiweather.current.model.CurrentWeatherFormatted
import jh.multiweather.current.model.CurrentWeatherState
import jh.multiweather.current.platform.CurrentWeatherController
import jh.multiweather.shared.model.WeatherDescription.UNKNOWN
import jh.multiweather.shared.model.toWeatherDescription
import jh.multiweather.shared.platform.InputController
import jh.shared.datetime.infrastructure.dateTimeFormatterOfPattern
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.listeners.infrastructure.MutableObservable
import kotlin.math.roundToInt

@Singleton
class CurrentWeatherViewModel @Inject constructor(
        private val currentWeatherController: CurrentWeatherController,
        private val inputController: InputController
) {
    companion object {
        private const val API_KEY = "060babdcb0097cb661c39c2c9e6c4a09"
    }

    private val statesObservable = MutableObservable(notifyLastEmissionOnSubscription = true, defaultValue = CurrentWeatherState())

    val states = statesObservable.observable

    fun refresh() {
        statesObservable.post(CurrentWeatherState(isLoadingVisible = true))

        currentWeatherController.load(API_KEY, inputController.city)
                .subscribe {
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
                            .let {
                                statesObservable.post(CurrentWeatherState(
                                        currentWeather = it,
                                        isCurrentWeatherVisible = true,
                                        isLoadingVisible = false
                                ))
                            }
                }
    }
}

