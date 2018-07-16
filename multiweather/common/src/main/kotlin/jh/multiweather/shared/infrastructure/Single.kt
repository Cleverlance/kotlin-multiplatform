package jh.multiweather.shared.infrastructure

expect abstract class Single<T : Any> {
    fun observeOn(scheduler: Scheduler): Single<T>
}

expect fun <T : Any, R : Any> Single<T>.map(mapper: (T) -> R): Single<R>
expect fun <T : Any> Single<T>.subscribe(onNext: (T) -> Unit, onError: (Throwable) -> Unit): Disposable