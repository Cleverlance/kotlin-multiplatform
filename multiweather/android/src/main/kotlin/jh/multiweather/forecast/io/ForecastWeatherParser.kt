package jh.multiweather.forecast.io

import jh.multiweather.forecast.model.ForecastWeather
import jh.shared.datetime.infrastructure.toZonedDateTime
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
actual class ForecastWeatherParser @Inject actual constructor() {
    actual fun parse(data: String): List<ForecastWeather> = JSON.nonstrict.parse<ForecastWeatherRemote>(data)
            .let {
                it.forecastItems.map { item ->
                    ForecastWeather(
                            item.timestamp?.toZonedDateTime(),
                            it.city.name,
                            item.mainParameters.temperatureMinCelsius,
                            item.mainParameters.temperatureMaxCelsius,
                            item.weatherDescriptions.first().text,
                            item.weatherDescriptions.first().code
                    )
                }
            }

    @Serializable
    private data class ForecastWeatherRemote(
            @SerialName("city") val city: City,
            @SerialName("list") val forecastItems: List<ForecastItem>
    ) {
        @Serializable
        data class City(
                @Optional @SerialName("name") val name: String? = null
        )

        @Serializable
        data class ForecastItem(
                @Optional @SerialName("dt") val timestamp: Long? = null,
                @SerialName("main") val mainParameters: MainParameters,
                @SerialName("weather") val weatherDescriptions: List<WeatherDescription>
        )

        @Serializable
        data class MainParameters(
                @Optional @SerialName("temp_min") val temperatureMinCelsius: Double? = null,
                @Optional @SerialName("temp_max") val temperatureMaxCelsius: Double? = null
        )

        @Serializable
        data class WeatherDescription(
                @Optional @SerialName("id") val code: Int? = null,
                @Optional @SerialName("description") val text: String? = null
        )
    }
}