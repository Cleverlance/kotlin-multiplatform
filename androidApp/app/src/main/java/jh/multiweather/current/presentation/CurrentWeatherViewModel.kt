package jh.multiweather.current.presentation

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
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

@Singleton
class CurrentWeatherViewModel @Inject constructor() {

    // TODO real data
    val currentWeatherFormattedTexts: Observable<Optional<CurrentWeatherFormatted>> = Observable.just(
            CurrentWeatherFormatted(
                    "26. 3. 2018, 15:23",
                    "Brno",
                    "18 °C",
                    "1024 mBar",
                    "Clear",
                    "clear sky",
                    CurrentWeatherFormatted.DescriptionIcon.CLEAR,
                    "6.5 kmph",
                    "120 °",
                    "26. 3. 2018, 7:21",
                    "26. 3. 2018, 20:13"
            ).toOptional()
    )
    val currentWeatherFormattedVisibles: Observable<Boolean> = Observable.just(true)

    val isLoadingVisibles: Observable<Boolean> = Observable.just(false)

    val errorMessageTexts: Observable<Optional<String>> = Observable.just(None)
    val errorMessageVisibles: Observable<Boolean> = Observable.just(false)

    fun refresh() {
        Timber.d("Refresh")

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
                            it.timestamp.toZonedDateTime(),
                            it.location,
                            it.mainParameters.temperatureCelsius,
                            it.mainParameters.pressureMilliBar,
                            it.weatherDescriptions.first().short,
                            it.weatherDescriptions.first().long,
                            it.weatherDescriptions.first().code,
                            it.wind.speedKmph,
                            it.wind.directionDegrees,
                            it.sun.sunriseTimestamp.toZonedDateTime(),
                            it.sun.sunsetTimestamp.toZonedDateTime()
                    )
                }
                .map {
                    CurrentWeatherFormatted(
                            it.timestamp.format(DateTimeFormatter.ofPattern("dd. LLLL H:mm")),
                            it.location,
                            "${it.temperatureCelsius.roundToInt()} °C",
                            "${it.pressureMilliBar.roundToInt()} mBar",
                            it.descriptionShort,
                            it.descriptionLong,
                            it.descriptionCode.toDescriptionIcon(),
                            "${it.windSpeedKmph} km/h",
                            "${it.windDirectionDegrees.roundToInt()}°",
                            it.sunriseTimestamp.format(DateTimeFormatter.ofPattern("H:mm")),
                            it.sunsetTimestamp.format(DateTimeFormatter.ofPattern("H:mm"))
                    )
                }
                .observeOn(mainThread())
                .subscribe({
                    Timber.d("Success: $it")
                }, {
                    Timber.e("Error: $it")
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
        @SerialName("dt") val timestamp: Long,
        @SerialName("name") val location: String,
        @SerialName("main") val mainParameters: MainParameters,
        @SerialName("weather") val weatherDescriptions: List<WeatherDescription>,
        @SerialName("wind") val wind: Wind,
        @SerialName("sys") val sun: Sun
) {
    @Serializable
    data class WeatherDescription(
            @SerialName("id") val code: Int,
            @SerialName("main") val short: String,
            @SerialName("description") val long: String
    )

    @Serializable
    data class MainParameters(
            @SerialName("temp") val temperatureCelsius: Double,
            @SerialName("pressure") val pressureMilliBar: Double
    )

    @Serializable
    data class Wind(
            @SerialName("speed") val speedKmph: Double,
            @SerialName("deg") val directionDegrees: Double
    )

    @Serializable
    data class Sun(
            @SerialName("sunrise") val sunriseTimestamp: Long,
            @SerialName("sunset") val sunsetTimestamp: Long
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