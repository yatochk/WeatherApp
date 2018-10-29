package com.yatochk.weather.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import kotlin.concurrent.thread

const val DELAYED: Long = 2000

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler()
        thread {
            val intent = Intent(this, MainActivity::class.java)
            handler.postDelayed(
                {
                    startActivity(intent)
                    finish()
                },
                DELAYED
            )
        }.start()
    }
}