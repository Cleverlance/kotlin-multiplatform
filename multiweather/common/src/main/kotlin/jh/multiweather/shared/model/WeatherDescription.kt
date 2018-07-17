package jh.multiweather.shared.model

import jh.multiweather.shared.model.WeatherDescription.*

enum class WeatherDescription {
    CLEAR,
    FOG,
    FEW_CLOUDS,
    SCATTERED_CLOUDS,
    OVERCAST_CLOUDS,
    DRIZZLE,
    LIGHT_RAIN,
    HEAVY_RAIN,
    SNOW,
    THUNDERSTORM,
    UNKNOWN
}

fun Int.toWeatherDescription() = when (this) {
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