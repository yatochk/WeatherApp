package com.yatochk.weather.dagger.modules

import com.yatochk.weather.dagger.App
import com.yatochk.weather.model.Model
import dagger.Module
import dagger.Provides

@Module
class ModelModule {
    @Provides
    fun provideModel(): Model = Model(App.component.getContext())
}