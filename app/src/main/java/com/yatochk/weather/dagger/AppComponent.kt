package com.yatochk.weather.dagger

import com.yatochk.weather.dagger.modules.*
import com.yatochk.weather.presenter.DialogPresenter
import com.yatochk.weather.presenter.MainPresenter
import com.yatochk.weather.presenter.SettingsPresenter
import com.yatochk.weather.presenter.WeatherPresenter
import dagger.Component

@Component(
    modules = [MainPresenterModule::class, WeatherPresenterModule::class, SettingsPresenterModule::class,
        DialogPresenterModule::class, ModelModule::class, WeatherModule::class]
)
interface AppComponent {
    fun getMainPresenter(): MainPresenter
    fun getWeatherPresenter(): WeatherPresenter
    fun getSettingsPresenter(): SettingsPresenter
    fun getDialogPresenter(): DialogPresenter
}