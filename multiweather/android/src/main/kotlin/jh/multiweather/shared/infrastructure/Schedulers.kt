package jh.multiweather.shared.infrastructure

import io.reactivex.schedulers.Schedulers

actual object Schedulers {
    actual fun computation(): Scheduler = Schedulers.computation()
}