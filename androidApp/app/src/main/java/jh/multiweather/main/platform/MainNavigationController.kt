package jh.multiweather.main.platform

import jh.multiweather.arch.model.Screen
import jh.multiweather.arch.platform.NavigationController
import jh.multiweather.current.ui.CurrentWeatherFragment
import jh.multiweather.main.model.ScreenData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigationController @Inject constructor() : NavigationController<ScreenData>() {
    override val defaultScreen = Screen(CurrentWeatherFragment::class, false)
}