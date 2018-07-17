package jh.shared.rx.infrastructure

import io.reactivex.subjects.BehaviorSubject

actual typealias BehaviorSubject<T> = BehaviorSubject<T>

actual fun <T : Any> createBehaviorSubject(defaultValue: T?) =
        defaultValue?.let { BehaviorSubject.createDefault(defaultValue) } ?: BehaviorSubject.create()