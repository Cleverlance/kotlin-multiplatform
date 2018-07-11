package jh.greeting

class Greeting {
    fun greeting(): String = "Hello, ${Platform().platform}"
}