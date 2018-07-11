package jh.multiweather.main.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import jh.multiweather.current.di.CurrentComponent
import jh.multiweather.main.ui.MainActivity
import jh.multiweather.main.ui.MainApplication
import javax.inject.Singleton

@Singleton
@Component
interface MainComponent : CurrentComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): MainComponent
    }

    fun inject(application: MainApplication)

    fun inject(activity: MainActivity)
}