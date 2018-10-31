package com.yatochk.weather.view.cities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.MainPresenter

class CitiesWeatherActivity : AppCompatActivity(), CitiesView {
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = App.component.getPresenter()
    }
}
