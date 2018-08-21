package jh.shared.navigation.platform

import jh.shared.listeners.infrastructure.MutableObservable
import jh.shared.listeners.infrastructure.Observable
import jh.shared.navigation.model.Screen

abstract class NavigationController<S : Any> {

    private var screensObservable = MutableObservable<Screen<S>>()
    private var backsObservable = MutableObservable<Unit>()

    val screens: Observable<Screen<S>> = screensObservable.observable
    val backs: Observable<Unit> = backsObservable.observable

    abstract val defaultScreen: Screen<S>

    fun goTo(screen: S, addToBackStack: Boolean = true) {
        screensObservable.post(Screen(screen, addToBackStack))
    }

    fun back() {
        backsObservable.post(Unit)
    }
}