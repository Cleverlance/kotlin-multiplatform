package jh.multiweather.forecast.model

data class ForecastWeatherState(
        val forecastWeathers: List<ForecastWeatherFormatted>? = null,
        val isForecastWeatherVisible: Boolean = false,
        val isLoadingVisible: Boolean = false,
        val errorMessage: String? = null,
        val isErrorMessageVisible: Boolean = false
)