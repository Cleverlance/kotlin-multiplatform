package jh.multiweather.arch.platform

import jh.shared.navigation.model.Screen
import jh.shared.navigation.platform.NavigationController
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NavigationControllerTest {

    private val anyScreen = "anyScreen"
    private val otherScreen = "otherScreen"
    private val controller = object : NavigationController<String>() {
        override val defaultScreen = Screen(anyScreen, false)
    }

    @Test
    fun `screens observable should not send any events after construction`() {
        controller.screens.test()
                .assertEmpty()
    }

    @Test
    fun `backs observable should not send any events after construction`() {
        controller.backs.test()
                .assertEmpty()
    }

    @Test
    fun `should notify about given screens`() {
        val screensObserver = controller.screens.test()

        controller.goTo(anyScreen, true)
        controller.goTo(anyScreen, false)
        controller.goTo(otherScreen)
        controller.goTo(anyScreen)

        screensObserver
                .assertValues(Screen(anyScreen, true), Screen(anyScreen, false), Screen(otherScreen, true), Screen(anyScreen, true))
                .assertNotTerminated()
    }

    @Test
    fun `should notify about each back`() {
        val backsObserver = controller.backs.test()

        controller.back()
        controller.back()

        backsObserver
                .assertValues(Unit, Unit)
                .assertNotTerminated()
    }
}