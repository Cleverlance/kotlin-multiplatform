package jh.shared.locale.platform

import platform.Foundation.*
import jh.shared.locale.model


actual class LocaleController {

    actual fun getCurrentLocale() = Locale(NSLocale.current.identifier)
}
