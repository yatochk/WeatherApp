package com.yatochk.weather.dagger

import com.yatochk.weather.model.onlineweather.OnlineWeather
import dagger.Module
import dagger.Provides

@Module
class WeatherModule {
    @Provides
    fun provideWeather(): OnlineWeather = OnlineWeather()
}