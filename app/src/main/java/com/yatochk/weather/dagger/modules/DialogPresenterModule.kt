package com.yatochk.weather.dagger.modules

import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.DialogPresenter
import dagger.Module
import dagger.Provides

@Module
class DialogPresenterModule {
    @Provides
    fun provideDialogPresenter(): DialogPresenter =
        DialogPresenter(App.component.getModel())
}