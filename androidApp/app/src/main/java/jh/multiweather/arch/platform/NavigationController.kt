package jh.multiweather.arch.platform

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import jh.multiweather.arch.model.Screen
import timber.log.Timber

abstract class NavigationController<S : Any> {

    private var screensRelay = PublishRelay.create<Screen<S>>()
    private var backsRelay = PublishRelay.create<Unit>()

    val screens: Observable<Screen<S>> = screensRelay.hide()
    val backs: Observable<Unit> = backsRelay.hide()

    abstract val defaultScreen: Screen<S>

    fun goTo(screen: S, addToBackStack: Boolean = true) {
        Timber.i("Go to: $screen")

        screensRelay.accept(Screen(screen, addToBackStack))
    }

    fun back() {
        Timber.i("Back")

        backsRelay.accept(Unit)
    }
}