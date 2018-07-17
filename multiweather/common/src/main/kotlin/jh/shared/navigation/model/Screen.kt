package jh.shared.navigation.model

data class Screen<out S>(val data: S, val addToBackStack: Boolean)