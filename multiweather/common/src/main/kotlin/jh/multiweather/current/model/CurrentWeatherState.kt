package jh.multiweather.current.model

data class CurrentWeatherState(
        val currentWeather: CurrentWeatherFormatted? = null,
        val isCurrentWeatherVisible: Boolean = false,
        val isLoadingVisible: Boolean = false,
        val errorMessage: String? = null,
        val isErrorMessageVisible: Boolean = false
)