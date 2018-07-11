package jh.multiweather.current.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import jh.multiplatform.R
import jh.multiweather.arch.ui.RxFragment
import jh.multiweather.current.presentation.CurrentWeatherViewModel
import jh.multiweather.main.ui.MainApplication
import kotlinx.android.synthetic.main.current__current_weather_fragment.*

class CurrentWeatherFragment : RxFragment<CurrentWeatherViewModel>() {

    override val layoutResId = R.layout.current__current_weather_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }

    override fun bindViewModelToUi() = with(viewModel) {
        listOf(
                currentWeatherFormattedTexts.subscribe {
                    with(it.toNullable()) {
                        date.text = this?.date
                        temperature.text = this?.temperatureCelsius
                        description.text = this?.description
                    }
                },
                currentWeatherFormattedVisibles.subscribe {
                    listOf(date, temperature, description).forEach { view -> view.visibility = if (it) VISIBLE else GONE }
                },
                isLoadingVisibles.subscribe {
                    progressBar.visibility = if (it) VISIBLE else GONE
                },
                errorMessageTexts.subscribe {
                    errorMessage.text = it.toNullable()
                },
                errorMessageVisibles.subscribe {
                    errorMessage.visibility = if (it) VISIBLE else GONE
                }
        )
    }
}