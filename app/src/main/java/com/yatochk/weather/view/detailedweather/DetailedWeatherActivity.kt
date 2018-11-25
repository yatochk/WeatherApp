package com.yatochk.weather.view.detailedweather

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.presenter.WeatherPresenter

class DetailedWeatherActivity : AppCompatActivity(), DetailedWeatherView {
    override fun setWeather(cityWeather: CityWeather) {
        
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    lateinit var presenter: WeatherPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed_weather)
        presenter = App.component.weatherPresenter
    }

    override fun onResume() {
        super.onResume()
    }
}
