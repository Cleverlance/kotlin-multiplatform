package jh.shared.listeners.infrastructure

interface Subscription {

    fun unsubscribe()
}

fun MutableList<Subscription>.unsubscribeAllAndClear() {
    forEach { it.unsubscribe() }
    clear()
}