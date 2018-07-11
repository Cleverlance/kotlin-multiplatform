package jh.multiweather.current.presentation

import com.gojuno.koptional.None
import com.gojuno.koptional.Optional
import com.gojuno.koptional.toOptional
import io.reactivex.Observable
import jh.multiweather.current.model.CurrentWeatherFormatted
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
}