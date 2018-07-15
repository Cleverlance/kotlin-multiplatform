package jh.multiweather.shared.infrastructure

expect object Schedulers {
    fun computation(): Scheduler
}