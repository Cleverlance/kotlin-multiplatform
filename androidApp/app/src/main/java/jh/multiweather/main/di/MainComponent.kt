package jh.multiweather.main.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import jh.multiweather.current.di.CurrentComponent
import jh.multiweather.forecast.di.ForecastComponent
import jh.multiweather.main.ui.MainActivity
import jh.multiweather.main.ui.MainApplication
import jh.multiweather.overview.di.OverviewComponent
import javax.inject.Singleton

@Singleton
@Component
interface MainComponent : OverviewComponent, CurrentComponent, ForecastComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): MainComponent
    }

    fun inject(application: MainApplication)

    fun inject(activity: MainActivity)
}