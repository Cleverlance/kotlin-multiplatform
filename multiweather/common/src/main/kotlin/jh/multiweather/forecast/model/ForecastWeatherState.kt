package jh.multiweather.forecast.model

data class ForecastWeatherState(
        val forecastWeather: ForecastWeatherFormatted? = null,
        val isForecastWeatherVisible: Boolean = false,
        val isLoadingVisible: Boolean = true,
        val errorMessage: String? = null,
        val isErrorMessageVisible: Boolean = false
)