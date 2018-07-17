package jh.multiweather.current.io

import io.reactivex.schedulers.Schedulers
import jh.multiweather.shared.io.WeatherServiceParams.API_KEY_KEY
import jh.multiweather.shared.io.WeatherServiceParams.BASE_URL
import jh.multiweather.shared.io.WeatherServiceParams.CITY_KEY
import jh.multiweather.shared.io.WeatherServiceParams.CURRENT_WEATHER_PATH
import jh.multiweather.shared.io.WeatherServiceParams.LANGUAGE_KEY
import jh.multiweather.shared.io.WeatherServiceParams.UNITS_KEY
import jh.shared.rx.infrastructure.Single
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit.MILLISECONDS
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
                @Query(LANGUAGE_KEY) language: String,
                @Query(UNITS_KEY) units: String
        ): Single<ResponseBody>
    }

    actual fun load(apiKey: String, city: String, language: String, units: String): Single<String> = client.currentWeather(apiKey, city, language, units)
            .subscribeOn(Schedulers.io())
            .map { it.string() }
            .delay(500, MILLISECONDS) // for demonstration purposes only
}