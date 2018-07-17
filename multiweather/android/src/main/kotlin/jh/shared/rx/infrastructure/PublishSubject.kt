package jh.shared.rx.infrastructure

import io.reactivex.subjects.PublishSubject

actual typealias PublishSubject<T> = PublishSubject<T>

actual fun <T : Any> createPublishSubject() = PublishSubject.create<T>()