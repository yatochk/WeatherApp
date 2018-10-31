package com.yatochk.weather.dagger

import com.yatochk.weather.model.Model
import com.yatochk.weather.presenter.MainPresenter
import dagger.Component

@Component(modules = [MainPresenterModule::class, ModelModul::class, WeatherModule::class])
interface AppComponent {
    fun getPresenter(): MainPresenter
    fun getModel(): Model
}