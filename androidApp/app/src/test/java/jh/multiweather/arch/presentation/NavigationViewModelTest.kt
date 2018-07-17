package jh.multiweather.arch.presentation

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import jh.shared.navigation.model.Screen
import jh.shared.navigation.platform.NavigationController
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NavigationViewModelTest {

    private val anyBacksObservable = mock<Observable<Unit>>()
    private val anyScreensObservable = mock<Observable<Screen<String>>>()
    private val anyScreen = mock<Screen<String>>()
    private val anyNavigationController = mock<NavigationController<String>> {
        on { backs } doReturn anyBacksObservable
        on { screens } doReturn anyScreensObservable
        on { defaultScreen } doReturn anyScreen
    }

    private val viewModel = object : NavigationViewModel<String>(anyNavigationController) {}

    @Test
    fun `should call back on controller`() {
        viewModel.back()

        verify(anyNavigationController).back()
    }

    @Test
    fun `should return backs observable from controller`() {
        assertEquals(anyBacksObservable, viewModel.backs)
    }

    @Test
    fun `should return screens observable from controller`() {
        assertEquals(anyScreensObservable, viewModel.screens)
    }

    @Test
    fun `should return default screen from controller`() {
        assertEquals(anyScreen, viewModel.defaultScreen)
    }
}