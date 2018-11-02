package com.yatochk.weather.dagger

import com.yatochk.weather.model.Model
import com.yatochk.weather.presenter.SettingsPresenter
import dagger.Module
import dagger.Provides

@Module
class SettingsPresenterModule {
    @Provides
    fun provideMainPresenter(model: Model): SettingsPresenter =
        SettingsPresenter(model)
}