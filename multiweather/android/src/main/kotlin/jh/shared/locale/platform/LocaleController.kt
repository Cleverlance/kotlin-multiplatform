package jh.shared.locale.platform

import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
actual class LocaleController @Inject actual constructor() {
    actual fun getCurrentLocale(): Locale = Locale.getDefault()
}