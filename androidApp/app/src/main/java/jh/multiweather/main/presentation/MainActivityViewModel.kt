package jh.multiweather.main.presentation

import jh.shared.navigation.model.ScreenData
import jh.shared.navigation.platform.MainNavigationController
import jh.shared.navigation.presentation.NavigationViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivityViewModel @Inject constructor(navigationController: MainNavigationController) : NavigationViewModel<ScreenData>(navigationController)