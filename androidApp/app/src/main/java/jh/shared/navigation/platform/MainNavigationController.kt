package jh.shared.navigation.platform

import jh.multiweather.current.ui.CurrentWeatherFragment
import jh.shared.navigation.model.Screen
import jh.shared.navigation.model.ScreenData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigationController @Inject constructor() : NavigationController<ScreenData>() {
    override val defaultScreen = Screen(CurrentWeatherFragment::class, false)
}