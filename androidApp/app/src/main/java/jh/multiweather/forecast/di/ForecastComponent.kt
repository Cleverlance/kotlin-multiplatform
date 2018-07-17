package jh.multiweather.forecast.di

import jh.multiweather.forecast.ui.ForecastWeatherFragment

interface ForecastComponent {

    fun inject(fragment: ForecastWeatherFragment)
}