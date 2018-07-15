package jh.multiweather.shared.infrastructure

import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

actual typealias Single<T> = Single<T>

actual fun <T : Any> Single<T>.observeOnComputation(): Single<T> = observeOn(Schedulers.computation())
actual fun <T : Any, R : Any> Single<T>.map(mapper: (T) -> R): Single<R> = map(mapper)