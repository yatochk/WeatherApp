package com.yatochk.weather.dagger.modules

import com.yatochk.weather.model.Model
import com.yatochk.weather.model.onlineweather.OnlineWeather
import dagger.Module
import dagger.Provides

@Module
class ModelModule {
    @Provides
    fun provideModel(onlineWeather: OnlineWeather): Model = Model(onlineWeather)
}