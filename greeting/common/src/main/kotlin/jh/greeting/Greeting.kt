package jh.greeting

// TODO testing common code using mocks?
class Greeting {
    fun greeting(): String = "Hello, ${Platform().platform}"
}