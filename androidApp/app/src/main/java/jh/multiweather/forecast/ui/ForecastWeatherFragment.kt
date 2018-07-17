package jh.multiweather.forecast.ui

import android.os.Bundle
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import jh.multiplatform.R
import jh.multiweather.forecast.presentation.ForecastWeatherViewModel
import jh.multiweather.main.ui.MainApplication
import jh.shared.arch.ui.RxFragment
import timber.log.Timber

class ForecastWeatherFragment : RxFragment<ForecastWeatherViewModel>() {

    override val layoutResId = R.layout.forecast__forecast_weather_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh()
    }

    override fun bindViewModelToUi() = listOf(
            viewModel.states
                    // TODO remove observeOn operator when RxAndroid issues are resolved
                    .observeOn(mainThread())
                    .subscribe {
                        Timber.d("*** Forecast: $it")
                    }
    )
}