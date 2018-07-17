package jh.multiweather.current.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import jh.multiplatform.R
import jh.multiweather.current.model.CurrentWeatherFormatted.DescriptionIcon.*
import jh.multiweather.current.presentation.CurrentWeatherViewModel
import jh.multiweather.main.ui.MainApplication
import jh.shared.arch.ui.RxFragment
import kotlinx.android.synthetic.main.current__current_weather_fragment.*

// TODO add multiplatform logs
class CurrentWeatherFragment : RxFragment<CurrentWeatherViewModel>() {

    companion object {
        private val descriptionIconMap = mapOf(
                CLEAR to R.drawable.ic_clear,
                DRIZZLE to R.drawable.ic_drizzle,
                FEW_CLOUDS to R.drawable.ic_few_clouds,
                FOG to R.drawable.ic_fog,
                HEAVY_RAIN to R.drawable.ic_heavy_rain,
                LIGHT_RAIN to R.drawable.ic_light_rain,
                OVERCAST_CLOUDS to R.drawable.ic_overcast_clouds,
                SCATTERED_CLOUDS to R.drawable.ic_scattered_clouds,
                SNOW to R.drawable.ic_snow,
                THUNDERSTORM to R.drawable.ic_thunderstorm
        )
    }

    override val layoutResId = R.layout.current__current_weather_fragment

    override fun inject() {
        MainApplication.getInjector(context).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.menu.add(R.string.current__refresh).apply {
            setIcon(R.drawable.ic_refresh)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            setOnMenuItemClickListener {
                viewModel.refresh()
                true
            }
        }

        viewModel.refresh()
    }

    override fun bindViewModelToUi() = with(viewModel) {
        listOf(
                states
                        // TODO remove observeOn operator when RxAndroid issues are resolved
                        .observeOn(mainThread())
                        .subscribe {
                            it.currentWeather.let {
                                date.text = it?.timestamp
                                location.text = it?.location
                                temperature.text = it?.temperature
                                pressure.text = it?.pressure
                                description.text = it?.descriptionLong
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
}