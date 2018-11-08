package com.yatochk.weather.dagger.modules

import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainPresenterModule {
    @Provides
    fun provideMainPresenter(): MainPresenter = MainPresenter(App.component.getModel())
}