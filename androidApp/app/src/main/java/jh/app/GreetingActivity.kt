package jh.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import jh.greeting.Greeting
import jh.multiplatform.R
import kotlinx.android.synthetic.main.greeting__greeting_activity.*

class GreetingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.greeting__greeting_activity)

        greeting.text = Greeting().greeting()
    }
}
