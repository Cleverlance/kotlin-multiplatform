package jh.shared.locale.platform

import jh.shared.inject.infrastructure.Inject
import jh.shared.inject.infrastructure.Singleton
import jh.shared.locale.model.Locale

@Singleton
expect class LocaleController @Inject constructor() {
    fun getCurrentLocale(): Locale
}