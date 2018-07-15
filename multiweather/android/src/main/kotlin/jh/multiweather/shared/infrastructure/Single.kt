package jh.multiweather.shared.infrastructure

import io.reactivex.Single

actual typealias Single<T> = Single<T>

actual fun <T : Any, R : Any> Single<T>.map(mapper: (T) -> R): Single<R> = map(mapper)