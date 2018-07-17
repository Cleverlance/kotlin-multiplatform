package jh.multiweather.main.platform

import jh.multiweather.main.model.ScreenData
import jh.multiweather.overview.ui.OverviewFragment
import jh.shared.navigation.model.Screen
import jh.shared.navigation.platform.NavigationController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigationController @Inject constructor() : NavigationController<ScreenData>() {
    override val defaultScreen = Screen(OverviewFragment::class, false)
}