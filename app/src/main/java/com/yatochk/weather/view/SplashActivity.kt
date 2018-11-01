package com.yatochk.weather.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.yatochk.weather.model.database.CityWeatherEntry
import com.yatochk.weather.view.cities.CitiesWeatherActivity

const val DELAYED: Long = 2000

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val handler = Handler()
        val intent = Intent(this, CitiesWeatherActivity::class.java)
        val projection = arrayOf(CityWeatherEntry.ID, CityWeatherEntry.CITY)

        handler.postDelayed(
            {
                startActivity(intent)
                finish()
            },
            DELAYED
        )

        val cursor = contentResolver.query(
            CityWeatherEntry.CONTENT_URI,
            projection,
            null,
            null,
            null
        ).close()
    }
}