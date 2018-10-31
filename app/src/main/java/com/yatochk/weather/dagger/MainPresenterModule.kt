package com.yatochk.weather.dagger

import com.yatochk.weather.model.Model
import com.yatochk.weather.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainPresenterModule {
    @Provides
    fun provideMainPresenter(model: Model): MainPresenter = MainPresenter(model)
}