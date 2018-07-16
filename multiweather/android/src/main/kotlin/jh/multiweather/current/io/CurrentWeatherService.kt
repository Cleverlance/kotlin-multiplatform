package jh.multiweather.current.io

import io.reactivex.schedulers.Schedulers
import jh.multiweather.current.io.CurrentWeatherServiceParams.API_KEY_KEY
import jh.multiweather.current.io.CurrentWeatherServiceParams.BASE_URL
import jh.multiweather.current.io.CurrentWeatherServiceParams.CITY_KEY
import jh.multiweather.current.io.CurrentWeatherServiceParams.CURRENT_WEATHER_PATH
import jh.multiweather.current.io.CurrentWeatherServiceParams.UNITS_KEY
import jh.shared.rx.infrastructure.Single
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
actual class CurrentWeatherService @Inject actual constructor() {

    private val client by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CurrentWeatherInterface::class.java)
    }

    private interface CurrentWeatherInterface {
        @GET(CURRENT_WEATHER_PATH)
        fun currentWeather(
                @Query(API_KEY_KEY) apiKey: String,
                @Query(CITY_KEY) city: String,
                @Query(UNITS_KEY) units: String
        ): Single<ResponseBody>
    }

    actual fun load(apiKey: String, city: String, units: String): Single<String> = client.currentWeather(apiKey, city, units)
            .subscribeOn(Schedulers.io())
            .map { it.string() }
            .delay(1000, TimeUnit.MILLISECONDS) // for demonstration purposes only
}