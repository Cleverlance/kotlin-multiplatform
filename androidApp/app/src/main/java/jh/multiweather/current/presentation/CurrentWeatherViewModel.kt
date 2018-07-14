package jh.multiweather.current.presentation

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import jh.multiweather.current.model.CurrentWeatherFormatted
import jh.multiweather.current.model.CurrentWeatherFormatted.DescriptionIcon.*
import jh.multiweather.current.platform.CurrentWeatherController
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class CurrentWeatherViewModel @Inject constructor(
        private val currentWeatherController: CurrentWeatherController
) {

    private val currentWeatherFormattedDataRelay = BehaviorRelay.createDefault<Optional<CurrentWeatherFormatted>>(None)
    private val currentWeatherFormattedVisiblesRelay = BehaviorRelay.createDefault(false)
    private val isLoadingVisiblesRelay = BehaviorRelay.createDefault(true)
    private val errorMessageTextsRelay = BehaviorRelay.createDefault<Optional<String>>(None)
    private val errorMessageVisiblesRelay = BehaviorRelay.createDefault(false)

    val currentWeatherFormattedData: Observable<Optional<CurrentWeatherFormatted>> = currentWeatherFormattedDataRelay.hide()
    val currentWeatherFormattedVisibles: Observable<Boolean> = currentWeatherFormattedVisiblesRelay.hide()

    val isLoadingVisibles: Observable<Boolean> = isLoadingVisiblesRelay.hide()

    val errorMessageTexts: Observable<Optional<String>> = errorMessageTextsRelay.hide()
    val errorMessageVisibles: Observable<Boolean> = errorMessageVisiblesRelay.hide()

    fun refresh() {
        Timber.d("Refresh")

        currentWeatherFormattedDataRelay.accept(None)
        currentWeatherFormattedVisiblesRelay.accept(false)
        isLoadingVisiblesRelay.accept(true)
        errorMessageTextsRelay.accept(None)
        errorMessageVisiblesRelay.accept(false)

        currentWeatherController.load("Brno")
                .map {
                    CurrentWeatherFormatted(
                            it.timestamp?.format(DateTimeFormatter.ofPattern("dd. LLLL H:mm")),
                            it.location,
                            it.temperatureCelsius?.let { "${it.roundToInt()} °C" },
                            it.pressureMilliBar?.let { "${it.roundToInt()} mBar" },
                            it.descriptionShort?.capitalize(),
                            it.descriptionLong?.capitalize(),
                            it.descriptionCode?.toDescriptionIcon() ?: UNKNOWN,
                            it.windSpeedKmph?.let { "$it km/h" },
                            it.windDirectionDegrees?.let { "${it.roundToInt()}°" },
                            it.sunriseTimestamp?.format(DateTimeFormatter.ofPattern("H:mm")),
                            it.sunsetTimestamp?.format(DateTimeFormatter.ofPattern("H:mm"))
                    )
                }
                .observeOn(mainThread())
                .subscribe({
                    Timber.d("Success: $it")

                    currentWeatherFormattedDataRelay.accept(it.toOptional())
                    currentWeatherFormattedVisiblesRelay.accept(true)
                    isLoadingVisiblesRelay.accept(false)
                    errorMessageTextsRelay.accept(None)
                    errorMessageVisiblesRelay.accept(false)
                }, {
                    Timber.e("Error: $it")

                    currentWeatherFormattedDataRelay.accept(None)
                    currentWeatherFormattedVisiblesRelay.accept(false)
                    isLoadingVisiblesRelay.accept(false)
                    errorMessageTextsRelay.accept(it.toString().toOptional())
                    errorMessageVisiblesRelay.accept(true)
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

