package com.yatochk.weather.view.detailedweather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yatochk.weather.R

class DetailedWeatherActivity : AppCompatActivity(), DetailedWeatherView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_weather)
    }
}
