package jh.multiweather.current.di

import jh.multiweather.current.ui.CurrentWeatherFragment

interface CurrentComponent {

    fun inject(fragment: CurrentWeatherFragment)
}