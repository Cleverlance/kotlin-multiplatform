package jh.multiweather.main.platform

import jh.multiweather.current.ui.CurrentWeatherFragment
import jh.shared.navigation.model.Screen
import jh.multiweather.main.model.ScreenData
import jh.shared.navigation.platform.NavigationController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigationController @Inject constructor() : NavigationController<ScreenData>() {
    override val defaultScreen = Screen(CurrentWeatherFragment::class, false)
}