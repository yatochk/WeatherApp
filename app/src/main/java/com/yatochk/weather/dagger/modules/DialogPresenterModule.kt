package com.yatochk.weather.dagger.modules

import com.yatochk.weather.model.Model
import com.yatochk.weather.presenter.DialogPresenter
import dagger.Module
import dagger.Provides

@Module
class DialogPresenterModule {
    @Provides
    fun provideDialogPresenter(model: Model): DialogPresenter =
        DialogPresenter(model)
}