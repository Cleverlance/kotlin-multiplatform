package jh.multiweather.current.model

data class CurrentWeatherFormatted(
        val timestamp: String,
        val location: String,
        val temperatureCelsius: String,
        val pressureMilliBar: String,
        val descriptionShort: String,
        val descriptionLong: String,
        val descriptionIcon: DescriptionIcon,
        val windSpeedKmph: String,
        val windDirectionDegrees: String,
        val sunriseTimestamp: String,
        val sunsetTimestamp: String
) {
    enum class DescriptionIcon {
        CLEAR,
        FOG,
        CLOUDS,
        DRIZZLE,
        RAIN,
        SNOW,
        THUNDERSTORM
    }
}