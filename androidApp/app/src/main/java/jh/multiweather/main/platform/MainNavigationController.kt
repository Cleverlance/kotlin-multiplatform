package jh.multiweather.main.platform

import jh.multiweather.arch.platform.NavigationController
import jh.multiweather.main.model.ScreenData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainNavigationController @Inject constructor() : NavigationController<ScreenData>()