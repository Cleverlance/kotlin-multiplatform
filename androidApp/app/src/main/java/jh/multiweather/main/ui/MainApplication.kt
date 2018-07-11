package jh.multiweather.main.ui

import android.app.Application
import android.content.Context
import jh.multiplatform.BuildConfig
import jh.multiweather.arch.infrastructure.TagPrefixDebugTree
import jh.multiweather.main.di.DaggerMainComponent
import jh.multiweather.main.di.MainComponent
import timber.log.Timber

class MainApplication : Application() {

    companion object {
        fun getInjector(context: Context?) = (context?.applicationContext as? MainApplication)
                ?.component
                ?: throw IllegalStateException("Cannot obtain injector when context is null")
    }

    private lateinit var component: MainComponent

    override fun onCreate() {
        super.onCreate()

        initDi()
        initTimber()

        Timber.i("Application created")
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(TagPrefixDebugTree("*MW: "))
        }
    }

    private fun initDi() {
        component = DaggerMainComponent.builder()
                .context(applicationContext)
                .build()

        component.inject(this)
    }
}