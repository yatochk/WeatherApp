package com.yatochk.weather.dagger

import android.content.Context
import com.yatochk.weather.dagger.modules.*
import com.yatochk.weather.model.Model
import com.yatochk.weather.presenter.DialogPresenter
import com.yatochk.weather.presenter.MainPresenter
import com.yatochk.weather.presenter.SettingsPresenter
import com.yatochk.weather.presenter.WeatherPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [MainPresenterModule::class, WeatherPresenterModule::class, SettingsPresenterModule::class,
        DialogPresenterModule::class, ModelModule::class, AppModule::class]
)
interface AppComponent {
    val mainPresenter: MainPresenter
    val weatherPresenter: WeatherPresenter
    val settingsPresenter: SettingsPresenter
    val dialogPresenter: DialogPresenter
    val model: Model
    val context: Context
}