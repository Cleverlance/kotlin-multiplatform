package jh.multiweather.arch.model

data class Screen<out S>(val data: S, val addToBackStack: Boolean)