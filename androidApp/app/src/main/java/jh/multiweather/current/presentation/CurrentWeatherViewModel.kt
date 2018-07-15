package jh.multiweather.current.presentation

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.subjects.BehaviorSubject
import jh.multiweather.current.model.CurrentWeatherFormatted
import jh.multiweather.current.model.CurrentWeatherFormatted.DescriptionIcon.*
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class CurrentWeatherViewModel @Inject constructor(
        private val currentWeatherController: CurrentWeatherController
) {

    private val currentWeatherFormattedDataSubject = BehaviorSubject.createDefault<Optional<CurrentWeatherFormatted>>(None)
    private val currentWeatherFormattedVisiblesSubject = BehaviorSubject.createDefault(false)
    private val isLoadingVisiblesSubject = BehaviorSubject.createDefault(true)
    private val errorMessageTextsSubject = BehaviorSubject.createDefault<Optional<String>>(None)
    private val errorMessageVisiblesSubject = BehaviorSubject.createDefault(false)

    val currentWeatherFormattedData: Observable<Optional<CurrentWeatherFormatted>> = currentWeatherFormattedDataSubject.hide()
    val currentWeatherFormattedVisibles: Observable<Boolean> = currentWeatherFormattedVisiblesSubject.hide()

    val isLoadingVisibles: Observable<Boolean> = isLoadingVisiblesSubject.hide()

    val errorMessageTexts: Observable<Optional<String>> = errorMessageTextsSubject.hide()
    val errorMessageVisibles: Observable<Boolean> = errorMessageVisiblesSubject.hide()

    fun refresh() {
        Timber.d("Refresh")

        currentWeatherFormattedDataSubject.onNext(None)
        currentWeatherFormattedVisiblesSubject.onNext(false)
        isLoadingVisiblesSubject.onNext(true)
        errorMessageTextsSubject.onNext(None)
        errorMessageVisiblesSubject.onNext(false)

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

                    currentWeatherFormattedDataSubject.onNext(it.toOptional())
                    currentWeatherFormattedVisiblesSubject.onNext(true)
                    isLoadingVisiblesSubject.onNext(false)
                    errorMessageTextsSubject.onNext(None)
                    errorMessageVisiblesSubject.onNext(false)
                }, {
                    Timber.e("Error: $it")

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

