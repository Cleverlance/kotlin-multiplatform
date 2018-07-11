package jh.multiweather.arch.presentation

import jh.multiweather.arch.platform.NavigationController
import timber.log.Timber

abstract class NavigationViewModel<S : Any>(
        private val navigationController: NavigationController<S>
) {
    val screens = navigationController.screens
    val backs = navigationController.backs
    val defaultScreen = navigationController.defaultScreen

    fun back() {
        Timber.d("Back")

        navigationController.back()
    }
}