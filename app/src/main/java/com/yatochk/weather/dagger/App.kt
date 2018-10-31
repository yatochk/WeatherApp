package com.yatochk.weather.dagger

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
    }

    companion object {
        lateinit var component: AppComponent
            private set
    }
}