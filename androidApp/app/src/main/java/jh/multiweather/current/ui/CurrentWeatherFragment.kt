package jh.multiweather.current.ui

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import jh.multiplatform.R
import jh.multiweather.arch.ui.RxFragment
import jh.multiweather.current.model.CurrentWeatherFormatted.DescriptionIcon.*
import jh.multiweather.current.presentation.CurrentWeatherViewModel
import jh.multiweather.main.ui.MainApplication
import kotlinx.android.synthetic.main.current__current_weather_fragment.*

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

        viewModel.refresh()
    }

    override fun bindViewModelToUi() = with(viewModel) {
        listOf(
                currentWeatherFormattedData.subscribe {
                    with(it.toNullable()) {
                        date.text = this?.timestamp
                        location.text = this?.location
                        temperature.text = this?.temperatureCelsius
                        pressure.text = this?.pressureMilliBar
                        description.text = this?.descriptionLong
                        icon.setImageResource(descriptionIconMap.getOrDefault(this?.descriptionIcon ?: UNKNOWN, R.drawable.ic_unknown))
                        additionalInfo.text = "${getString(R.string.current__wind)} ${this?.windSpeedKmph} ${this?.windDirectionDegrees}  •  ${getString(R.string.current__sunrise)} ${this?.sunriseTimestamp}  •  ${getString(R.string.current__sunset)} ${this?.sunsetTimestamp}"
                    }
                },
                currentWeatherFormattedVisibles.subscribe {
                    listOf(date, location, temperature, pressure, description, icon, additionalInfo).forEach { view -> view.visibility = if (it) VISIBLE else GONE }
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