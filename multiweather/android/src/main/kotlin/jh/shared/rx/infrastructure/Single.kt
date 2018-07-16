package jh.shared.rx.infrastructure

import io.reactivex.Single

actual typealias Single<T> = Single<T>

actual fun <T : Any, R : Any> Single<T>.map(mapper: (T) -> R): Single<R> = map(mapper)
actual fun <T : Any> Single<T>.subscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable = subscribe(onNext, onError)