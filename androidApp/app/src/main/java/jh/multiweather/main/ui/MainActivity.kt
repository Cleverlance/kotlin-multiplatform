package jh.multiweather.main.ui

import android.os.Bundle
import jh.multiplatform.R
import jh.multiweather.main.presentation.MainActivityViewModel
import jh.shared.navigation.model.Screen
import jh.multiweather.main.model.ScreenData
import jh.shared.navigation.ui.NavigationActivity

class MainActivity : NavigationActivity<MainActivityViewModel, ScreenData>() {

    override val layoutResId = R.layout.main__main_activity

    override fun inject() {
        MainApplication.getInjector(this).inject(this)
    }

    override fun bindViewModelToUi() = super.bindViewModelToUi() +
            listOf(viewModel.screens.subscribe { placeScreen(it) })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState ?: let {
            placeScreen(viewModel.defaultScreen)
        }
    }

    private fun placeScreen(screen: Screen<ScreenData>) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainContainer, screen.data.java.newInstance())
                .apply {
                    if (screen.addToBackStack) {
                        addToBackStack(null)
                    } else {
                        disallowAddToBackStack()
                    }
                }
                .commit()
    }
}