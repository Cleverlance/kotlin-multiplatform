package jh.multiweather.current.presentation

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.computation
import io.reactivex.schedulers.Schedulers.io
import jh.multiweather.current.model.CurrentWeather
import jh.multiweather.current.model.CurrentWeatherFormatted
import jh.multiweather.current.model.CurrentWeatherFormatted.DescriptionIcon.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import okhttp3.ResponseBody
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt
import kotlinx.serialization.Optional as NotRequired

@Singleton
class CurrentWeatherViewModel @Inject constructor() {

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

        // TODO move
        Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CurrentWeatherService::class.java)
                .currentWeather("060babdcb0097cb661c39c2c9e6c4a09", "Brno", "metric")
                .subscribeOn(io())
                .observeOn(computation())
                .map { it.string() }
                .map {
                    JSON.nonstrict.parse<CurrentWeatherRemote>(it)
                }
                .map {
                    CurrentWeather(
                            it.timestamp?.toZonedDateTime(),
                            it.location,
                            it.mainParameters.temperatureCelsius,
                            it.mainParameters.pressureMilliBar,
                            it.weatherDescriptions.first().short,
                            it.weatherDescriptions.first().long,
                            it.weatherDescriptions.first().code,
                            it.wind.speedKmph,
                            it.wind.directionDegrees,
                            it.sun.sunriseTimestamp?.toZonedDateTime(),
                            it.sun.sunsetTimestamp?.toZonedDateTime()
                    )
                }
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

    private fun Long.toZonedDateTime() = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())

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

// TODO move
@Serializable
data class CurrentWeatherRemote(
        @NotRequired @SerialName("dt") val timestamp: Long? = null,
        @NotRequired @SerialName("name") val location: String? = null,
        @SerialName("main") val mainParameters: MainParameters,
        @SerialName("weather") val weatherDescriptions: List<WeatherDescription>,
        @SerialName("wind") val wind: Wind,
        @SerialName("sys") val sun: Sun
) {
    @Serializable
    data class WeatherDescription(
            @NotRequired @SerialName("id") val code: Int? = null,
            @NotRequired @SerialName("main") val short: String? = null,
            @NotRequired @SerialName("description") val long: String? = null
    )

    @Serializable
    data class MainParameters(
            @NotRequired @SerialName("temp") val temperatureCelsius: Double? = null,
            @NotRequired @SerialName("pressure") val pressureMilliBar: Double? = null
    )

    @Serializable
    data class Wind(
            @NotRequired @SerialName("speed") val speedKmph: Double? = null,
            @NotRequired @SerialName("deg") val directionDegrees: Double? = null
    )

    @Serializable
    data class Sun(
            @NotRequired @SerialName("sunrise") val sunriseTimestamp: Long? = null,
            @NotRequired @SerialName("sunset") val sunsetTimestamp: Long? = null
    )
}

// TODO move
interface CurrentWeatherService {
    @GET("weather")
    fun currentWeather(
            @Query("APPID") apiKey: String,
            @Query("q") city: String,
            @Query("units") units: String
    ): Observable<ResponseBody>
}