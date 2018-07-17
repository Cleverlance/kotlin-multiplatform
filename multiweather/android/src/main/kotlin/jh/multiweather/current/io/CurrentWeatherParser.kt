package jh.multiweather.current.io

import jh.multiweather.current.model.CurrentWeather
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
actual class CurrentWeatherParser @Inject actual constructor() {
    actual fun parse(data: String): CurrentWeather = JSON.nonstrict.parse<CurrentWeatherRemote>(data)
            .let {
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

    @Serializable
    private data class CurrentWeatherRemote(
            @Optional @SerialName("dt") val timestamp: Long? = null,
            @Optional @SerialName("name") val location: String? = null,
            @SerialName("main") val mainParameters: MainParameters,
            @SerialName("weather") val weatherDescriptions: List<WeatherDescription>,
            @SerialName("wind") val wind: Wind,
            @SerialName("sys") val sun: Sun
    ) {
        @Serializable
        data class WeatherDescription(
                @Optional @SerialName("id") val code: Int? = null,
                @Optional @SerialName("main") val short: String? = null,
                @Optional @SerialName("description") val long: String? = null
        )

        @Serializable
        data class MainParameters(
                @Optional @SerialName("temp") val temperatureCelsius: Double? = null,
                @Optional @SerialName("pressure") val pressureMilliBar: Double? = null
        )

        @Serializable
        data class Wind(
                @Optional @SerialName("speed") val speedKmph: Double? = null,
                @Optional @SerialName("deg") val directionDegrees: Double? = null
        )

        @Serializable
        data class Sun(
                @Optional @SerialName("sunrise") val sunriseTimestamp: Long? = null,
                @Optional @SerialName("sunset") val sunsetTimestamp: Long? = null
        )
    }

    // TODO remove duplication
    private fun Long.toZonedDateTime() = ZonedDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())
}