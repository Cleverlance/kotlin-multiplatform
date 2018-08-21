package jh.shared.listeners.infrastructure

interface Observable<out T> {

    fun subscribe(onValue: (T) -> Unit): Subscription
}