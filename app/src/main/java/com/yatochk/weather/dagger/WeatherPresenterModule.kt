package com.yatochk.weather.dagger

import com.yatochk.weather.model.Model
import com.yatochk.weather.presenter.WeatherPresenter
import dagger.Module
import dagger.Provides

@Module
class WeatherPresenterModule {
    @Provides
    fun provideWeatherPresenter(model: Model): WeatherPresenter =
        WeatherPresenter(model)
}