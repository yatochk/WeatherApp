package com.yatochk.weather.dagger.modules

import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class SettingsPresenterModule {
    @Provides
    fun provideMainPresenter(): SettingsPresenter =
        SettingsPresenter(App.component.getModel())
}