package jh.multiweather.current.presentation

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers.io
import jh.multiweather.current.model.CurrentWeatherFormatted
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentWeatherViewModel @Inject constructor() {

    // TODO real data
    val currentWeatherFormattedTexts: Observable<Optional<CurrentWeatherFormatted>> = Observable.just(CurrentWeatherFormatted("26. 3. 2018", "18 °C", "Light drizzle").toOptional())
    val currentWeatherFormattedVisibles: Observable<Boolean> = Observable.just(true)

    val isLoadingVisibles: Observable<Boolean> = Observable.just(false)

    val errorMessageTexts: Observable<Optional<String>> = Observable.just(None)
    val errorMessageVisibles: Observable<Boolean> = Observable.just(false)

    fun refresh() {
        Timber.d("Refresh")

        // TODO move
        Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(CurrentWeatherService::class.java)
                .currentWeather("060babdcb0097cb661c39c2c9e6c4a09", "Brno", "metric")
                .subscribeOn(io())
                .observeOn(mainThread())
                .subscribe({
                    Timber.d("Success: ${it.string()}")
                }, {
                    Timber.e("Error: $it")
                })
    }
}

// TODO move
interface CurrentWeatherService {
    @GET("weather")
    fun currentWeather(
            @Query("APPID") apiKey: String,
            @Query("q") city: String,
            @Query("units") units: String
    ): Observable<ResponseBody>
}