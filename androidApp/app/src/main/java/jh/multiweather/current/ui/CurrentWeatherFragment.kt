package jh.multiweather.current.ui

import jh.multiplatform.R
import jh.multiweather.arch.ui.RxFragment
import jh.multiweather.current.presentation.CurrentWeatherViewModel
import jh.multiweather.main.ui.MainApplication

class CurrentWeatherFragment : RxFragment<CurrentWeatherViewModel>() {

    override val layoutResId = R.layout.current__current_weather_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }
}