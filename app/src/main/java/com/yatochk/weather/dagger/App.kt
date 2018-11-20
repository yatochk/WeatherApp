package com.yatochk.weather.dagger

import android.app.Application
import com.yatochk.weather.dagger.modules.*

class App : Application() {
    companion object {
        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .modelModule(ModelModule())
            .mainPresenterModule(MainPresenterModule())
            .dialogPresenterModule(DialogPresenterModule())
            .settingsPresenterModule(SettingsPresenterModule())
            .weatherPresenterModule(WeatherPresenterModule())
            .build()
    }
}