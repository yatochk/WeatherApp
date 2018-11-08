package com.yatochk.weather.dagger.modules

import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.WeatherPresenter
import dagger.Module
import dagger.Provides

@Module
class WeatherPresenterModule {
    @Provides
    fun provideWeatherPresenter(): WeatherPresenter =
        WeatherPresenter(App.component.getModel())
}