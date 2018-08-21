package jh.shared.listeners.infrastructure

class MutableObservable<T>(private val notifyLastEmissionOnSubscription: Boolean = false, defaultValue: T? = null) : Observable<T> {

    private var lastValue: T? = null

    init {
        lastValue = defaultValue
    }

    private class Observer<T>(val onValue: (T) -> Unit, private val parent: MutableObservable<T>) : Subscription {
        override fun unsubscribe() {
            parent.unsubscribe(this)
        }
    }

    private val observers = mutableSetOf<Observer<T>>()

    fun post(value: T) {
        lastValue = value

        observers.forEach { it.onValue(value) }
    }

    fun unsubscribeAll() {
        observers.clear()
    }

    val observable: Observable<T> = this

    private fun unsubscribe(observer: Observer<T>) {
        observers -= observer
    }

    override fun subscribe(onValue: (T) -> Unit): Subscription =
            Observer(onValue, this).apply {
                observers += this
                if (notifyLastEmissionOnSubscription) {
                    lastValue?.let { onValue(it) }
                }
            }
}