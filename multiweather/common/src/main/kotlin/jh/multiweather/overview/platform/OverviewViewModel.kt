package jh.multiweather.overview.platform

import jh.multiweather.current.presentation.CurrentWeatherViewModel
import jh.multiweather.forecast.presentation.ForecastWeatherViewModel
import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton

@Singleton
class OverviewViewModel @Inject constructor(
        private val currentWeatherViewModel: CurrentWeatherViewModel,
        private val forecastWeatherViewModel: ForecastWeatherViewModel
) {

    fun refresh() {
        currentWeatherViewModel.refresh()
        forecastWeatherViewModel.refresh()
    }
}