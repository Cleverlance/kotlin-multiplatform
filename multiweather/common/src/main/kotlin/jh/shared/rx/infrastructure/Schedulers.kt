package jh.shared.rx.infrastructure

expect object Schedulers {
    fun computation(): Scheduler
    // TODO add mainThread scheduler (from RxAndroid - blocked by 'kotlin-platform-android' issue)
}