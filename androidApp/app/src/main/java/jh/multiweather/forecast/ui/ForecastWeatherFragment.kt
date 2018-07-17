package jh.multiweather.forecast.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import jh.multiplatform.R
import jh.multiweather.forecast.presentation.ForecastWeatherViewModel
import jh.multiweather.main.ui.MainApplication
import jh.shared.arch.ui.RxFragment
import kotlinx.android.synthetic.main.forecast__forecast_weather_fragment.*

class ForecastWeatherFragment : RxFragment<ForecastWeatherViewModel>() {

    override val layoutResId = R.layout.forecast__forecast_weather_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refresh()
    }

    override fun bindUiToViewModel() {
        super.bindUiToViewModel()

        with(forecasts) {
            layoutManager = LinearLayoutManager(context)
            adapter = ForecastWeatherAdapter(context)
        }
    }

    override fun bindViewModelToUi() = listOf(
            viewModel.states
                    // TODO remove observeOn operator when RxAndroid issues are resolved
                    .observeOn(mainThread())
                    .subscribe {
                        (forecasts.adapter as ForecastWeatherAdapter).items = it?.forecastWeather ?: listOf()
                        forecasts.visibility = if (it.isForecastWeatherVisible) VISIBLE else GONE
                        progressBar.visibility = if (it.isLoadingVisible) VISIBLE else GONE
                        errorMessage.text = it.errorMessage
                        errorMessage.visibility = if (it.isErrorMessageVisible) VISIBLE else GONE
                    }
    )
}