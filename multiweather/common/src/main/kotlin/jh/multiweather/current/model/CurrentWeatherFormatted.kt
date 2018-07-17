package jh.multiweather.current.model

data class CurrentWeatherFormatted(
        val timestamp: String?,
        val location: String?,
        val temperature: String?,
        val pressure: String?,
        val descriptionShort: String?,
        val descriptionLong: String?,
        val descriptionIcon: DescriptionIcon,
        val windSpeed: String?,
        val windDirection: String?,
        val sunriseTimestamp: String?,
        val sunsetTimestamp: String?
) {
    enum class DescriptionIcon {
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
}