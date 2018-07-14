package jh.multiweather.current.io

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers.io
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentWeatherService @Inject constructor() {

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val CURRENT_WEATHER_PATH = "weather"
        private const val API_KEY_KEY = "APPID"
        private const val CITY_KEY = "q"
        private const val UNITS_KEY = "units"

        const val METRIC_UNITS = "metric"
    }

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

    fun load(apiKey: String, city: String, units: String): Single<String> {
        Timber.d("Load: $apiKey, $city, $units")

        return client.currentWeather(apiKey, city, units)
                .subscribeOn(io())
                .map { it.string() }
                .delay(1000, MILLISECONDS) // for demonstration purposes only
    }
}