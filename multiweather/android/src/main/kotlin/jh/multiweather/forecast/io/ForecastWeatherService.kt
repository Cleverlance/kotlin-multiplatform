package jh.multiweather.forecast.io

import jh.multiweather.shared.io.WeatherServiceParams.API_KEY_KEY
import jh.multiweather.shared.io.WeatherServiceParams.BASE_URL
import jh.multiweather.shared.io.WeatherServiceParams.CITY_KEY
import jh.multiweather.shared.io.WeatherServiceParams.FORECAST_WEATHER_PATH
import jh.multiweather.shared.io.WeatherServiceParams.LANGUAGE_KEY
import jh.multiweather.shared.io.WeatherServiceParams.UNITS_KEY
import jh.shared.listeners.infrastructure.MutableObservable
import jh.shared.listeners.infrastructure.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
actual class ForecastWeatherService @Inject actual constructor() {

    private val client by lazy {
        Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()
                .create(ForecastWeatherInterface::class.java)
    }

    private val dataObservable = MutableObservable<String>()

    private interface ForecastWeatherInterface {
        @GET(FORECAST_WEATHER_PATH)
        fun forecastWeather(
                @Query(API_KEY_KEY) apiKey: String,
                @Query(CITY_KEY) city: String,
                @Query(LANGUAGE_KEY) language: String,
                @Query(UNITS_KEY) units: String
        ): Call<ResponseBody>
    }

    actual fun load(apiKey: String, city: String, language: String, units: String): Observable<String> {
        client.forecastWeather(apiKey, city, language, units)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.body()?.string()?.let { dataObservable.post(it) }
                    }

                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    }
                })

        return dataObservable.observable
    }
}