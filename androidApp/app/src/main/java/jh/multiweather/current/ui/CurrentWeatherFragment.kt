package jh.multiweather.current.ui

import android.view.View.GONE
import android.view.View.VISIBLE
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import jh.multiplatform.R
import jh.multiweather.current.presentation.CurrentWeatherViewModel
import jh.multiweather.main.ui.MainApplication
import jh.multiweather.shared.model.WeatherDescription.UNKNOWN
import jh.multiweather.shared.ui.descriptionIconMap
import jh.shared.arch.ui.RxFragment
import kotlinx.android.synthetic.main.current__current_weather_fragment.*

// TODO add multiplatform logs
class CurrentWeatherFragment : RxFragment<CurrentWeatherViewModel>() {

    override val layoutResId = R.layout.current__current_weather_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }

    override fun bindViewModelToUi() = listOf(
            viewModel.states
                    // TODO remove observeOn operator when RxAndroid issues are resolved
                    .observeOn(mainThread())
                    .subscribe {
                        it.currentWeather.let {
                            date.text = it?.timestamp
                            location.text = it?.location
                            temperature.text = it?.temperature
                            pressure.text = it?.pressure
                            description.text = it?.descriptionText
                            icon.setImageResource(descriptionIconMap.getOrDefault(it?.descriptionIcon
                                    ?: UNKNOWN, R.drawable.ic_unknown))
                            additionalInfo.text = "${getString(R.string.current__wind)} ${it?.windSpeed} ${it?.windDirection}  •  ${getString(R.string.current__sunrise)} ${it?.sunriseTimestamp}  •  ${getString(R.string.current__sunset)} ${it?.sunsetTimestamp}"
                        }
                        listOf(date, location, temperature, pressure, description, icon, additionalInfo).forEach { view -> view.visibility = if (it.isCurrentWeatherVisible) VISIBLE else GONE }
                        progressBar.visibility = if (it.isLoadingVisible) VISIBLE else GONE
                        errorMessage.text = it.errorMessage
                        errorMessage.visibility = if (it.isErrorMessageVisible) VISIBLE else GONE
                    }
    )
}