package jh.shared.rx.infrastructure

expect class PublishSubject<T : Any> {
    fun onNext(t: T)
    fun hide(): Observable<T>
}

// TODO how to expect Java static methods like PublishSubject.createDefault(...)?
expect fun <T : Any> createPublishSubject(): PublishSubject<T>