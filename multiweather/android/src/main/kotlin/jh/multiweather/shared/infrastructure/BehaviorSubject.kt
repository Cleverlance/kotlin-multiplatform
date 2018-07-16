package jh.multiweather.shared.infrastructure

import io.reactivex.subjects.BehaviorSubject

actual typealias BehaviorSubject<T> = BehaviorSubject<T>

actual fun <T : Any> createBehaviorSubject(defaultValue: T) = BehaviorSubject.createDefault(defaultValue)