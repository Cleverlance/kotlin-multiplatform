package jh.shared.navigation.platform

import jh.shared.navigation.model.Screen
import jh.shared.rx.infrastructure.Observable
import jh.shared.rx.infrastructure.createPublishSubject

abstract class NavigationController<S : Any> {

    private var screensRelay = createPublishSubject<Screen<S>>()
    private var backsRelay = createPublishSubject<Unit>()

    val screens: Observable<Screen<S>> = screensRelay.hide()
    val backs: Observable<Unit> = backsRelay.hide()

    abstract val defaultScreen: Screen<S>

    fun goTo(screen: S, addToBackStack: Boolean = true) {
        screensRelay.onNext(Screen(screen, addToBackStack))
    }

    fun back() {
        backsRelay.onNext(Unit)
    }
}