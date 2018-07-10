package org.konan.multiplatform

import org.greeting.Greeting
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class GreetingTest {

    @Test
    fun `should return android greeting`() {
        assertEquals("Hello, Android", Greeting().greeting())
    }
}
