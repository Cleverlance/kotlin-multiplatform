package jh.shared.navigation.presentation

import jh.shared.navigation.platform.NavigationController

abstract class NavigationViewModel<S : Any>(
        private val navigationController: NavigationController<S>
) {
    val screens = navigationController.screens
    val backs = navigationController.backs
    val defaultScreen = navigationController.defaultScreen

    fun back() {
        navigationController.back()
    }
}