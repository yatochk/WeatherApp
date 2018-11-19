package com.yatochk.weather.view.detailedweather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.WeatherPresenter

class DetailedWeatherActivity : AppCompatActivity(), DetailedWeatherView {
    lateinit var presenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_weather)
        presenter = App.component.getWeatherPresenter()
    }
}
