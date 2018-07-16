package jh.multiweather.shared.infrastructure

expect class BehaviorSubject<T : Any> {
    fun onNext(t: T)
    fun hide(): Observable<T>
}

// TODO how to expect Java static methods like BehaviorSubject.createDefault(...)?
expect fun <T : Any> createBehaviorSubject(defaultValue: T): BehaviorSubject<T>