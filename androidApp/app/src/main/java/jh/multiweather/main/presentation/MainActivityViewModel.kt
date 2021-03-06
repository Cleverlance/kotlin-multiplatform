package jh.multiweather.main.presentation

import jh.multiweather.main.model.ScreenData
import jh.multiweather.main.platform.MainNavigationController
import jh.shared.navigation.presentation.NavigationViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityViewModel @Inject constructor(navigationController: MainNavigationController) : NavigationViewModel<ScreenData>(navigationController)