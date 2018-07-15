package jh.multiweather.shared.infrastructure

expect abstract class Single<T : Any>

expect fun <T : Any> Single<T>.observeOnComputation(): Single<T>
expect fun <T : Any, R : Any> Single<T>.map(mapper: (T) -> R): Single<R>