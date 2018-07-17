package jh.multiweather.current.presentation

import jh.multiweather.current.model.CurrentWeatherFormatted
import jh.multiweather.current.model.CurrentWeatherFormatted.DescriptionIcon.*
import jh.multiweather.current.platform.CurrentWeatherController
import jh.shared.datetime.infrastructure.dateTimeFormatterOfPattern
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.optional.infrastructure.None
import jh.shared.optional.infrastructure.Optional
import jh.shared.optional.infrastructure.toOptional
import jh.shared.rx.infrastructure.Observable
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

    private val currentWeatherFormattedDataSubject = jh.shared.rx.infrastructure.createBehaviorSubject<Optional<CurrentWeatherFormatted>>(None)
    private val currentWeatherFormattedVisiblesSubject = jh.shared.rx.infrastructure.createBehaviorSubject(false)
    private val isLoadingVisiblesSubject = jh.shared.rx.infrastructure.createBehaviorSubject(true)
    private val errorMessageTextsSubject = jh.shared.rx.infrastructure.createBehaviorSubject<Optional<String>>(None)
    private val errorMessageVisiblesSubject = jh.shared.rx.infrastructure.createBehaviorSubject(false)

    val currentWeatherFormattedData: Observable<Optional<CurrentWeatherFormatted>> = currentWeatherFormattedDataSubject.hide()
    val currentWeatherFormattedVisibles: Observable<Boolean> = currentWeatherFormattedVisiblesSubject.hide()

    val isLoadingVisibles: Observable<Boolean> = isLoadingVisiblesSubject.hide()

    val errorMessageTexts: Observable<Optional<String>> = errorMessageTextsSubject.hide()
    val errorMessageVisibles: Observable<Boolean> = errorMessageVisiblesSubject.hide()

    fun refresh() {
        currentWeatherFormattedDataSubject.onNext(None)
        currentWeatherFormattedVisiblesSubject.onNext(false)
        isLoadingVisiblesSubject.onNext(true)
        errorMessageTextsSubject.onNext(None)
        errorMessageVisiblesSubject.onNext(false)

        currentWeatherController.load(API_KEY, "Brno")
                .map {
                    CurrentWeatherFormatted(
                            it.timestamp?.format(dateTimeFormatterOfPattern("dd. LLLL H:mm")),
                            it.location,
                            it.temperatureCelsius?.let { "${it.roundToInt()} °C" },
                            it.pressureMilliBar?.let { "${it.roundToInt()} mBar" },
                            it.descriptionShort?.capitalize(),
                            it.descriptionLong?.capitalize(),
                            it.descriptionCode?.toDescriptionIcon() ?: UNKNOWN,
                            it.windSpeedKmph?.let { "$it km/h" },
                            it.windDirectionDegrees?.let { "${it.roundToInt()}°" },
                            it.sunriseTimestamp?.format(dateTimeFormatterOfPattern("H:mm")),
                            it.sunsetTimestamp?.format(dateTimeFormatterOfPattern("H:mm"))
                    )
                }
                // TODO observe on mainThread when AndroidSchedulers are available in android module
                .subscribe({
                    currentWeatherFormattedDataSubject.onNext(it.toOptional())
                    currentWeatherFormattedVisiblesSubject.onNext(true)
                    isLoadingVisiblesSubject.onNext(false)
                    errorMessageTextsSubject.onNext(None)
                    errorMessageVisiblesSubject.onNext(false)
                }, {
                    currentWeatherFormattedDataSubject.onNext(None)
                    currentWeatherFormattedVisiblesSubject.onNext(false)
                    isLoadingVisiblesSubject.onNext(false)
                    errorMessageTextsSubject.onNext(it.toString().toOptional())
                    errorMessageVisiblesSubject.onNext(true)
                })
    }

    private fun Int.toDescriptionIcon() = when (this) {
        in 200..299 -> THUNDERSTORM
        in 300..399 -> DRIZZLE
        in 500..504 -> LIGHT_RAIN
        in 511..599 -> HEAVY_RAIN
        in 600..699 -> SNOW
        in 700..799 -> FOG
        800 -> CLEAR
        801 -> FEW_CLOUDS
        802 -> SCATTERED_CLOUDS
        in 803..804 -> OVERCAST_CLOUDS
        else -> UNKNOWN
    }
}

