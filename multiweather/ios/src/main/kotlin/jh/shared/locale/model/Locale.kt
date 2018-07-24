package jh.shared.locale.model

actual class Locale(private val locale: String) {
    actual fun getLanguage() = locale
}
