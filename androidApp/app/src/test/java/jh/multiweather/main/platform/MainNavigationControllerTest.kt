package jh.multiweather.main.platform

import jh.multiweather.arch.model.Screen
import jh.multiweather.current.ui.CurrentWeatherFragment
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainNavigationControllerTest {

    @Test
    fun `should return correct default screen`() {
        assertEquals(Screen(CurrentWeatherFragment::class, false), MainNavigationController().defaultScreen)
    }
}