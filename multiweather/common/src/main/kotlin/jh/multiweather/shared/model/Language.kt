package jh.multiweather.shared.model

import jh.multiweather.shared.model.Language.ENGLISH
import jh.multiweather.shared.model.Language.values
import jh.shared.locale.model.Locale

enum class Language(val apiCode: String, val systemCode: String = apiCode) {
    ARABIC("ar"),
    BULGARIAN("bg"),
    CATALAN("ca"),
    CZECH("cz", "cs"),
    GERMAN("de"),
    GREEK("el"),
    ENGLISH("en"),
    PERSIAN("fa"),
    FINNISH("fi"),
    FRENCH("fr"),
    GALICIAN("gl"),
    CROATIAN("hr"),
    HUNGARIAN("hu"),
    ITALIAN("it"),
    JAPANESE("ja"),
    KOREAN("kr", "ko"),
    LATVIAN("la", "lv"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    DUTCH("nl"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SWEDISH("se", "sv"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SPANISH("es"),
    TURKISH("tr"),
    UKRAINIAN("ua", "uk"),
    VIETNAMESE("vi"),
    CHINESE_SIMPLIFIED("zh_cn", "zh")
}

fun Locale.toLanguage() = values().find { it.systemCode.equals(getLanguage(), ignoreCase = true) } ?: ENGLISH