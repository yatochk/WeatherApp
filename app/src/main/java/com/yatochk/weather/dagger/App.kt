package com.yatochk.weather.dagger

import android.app.Application

class App : Application() {
    companion object {
        lateinit var component: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.create()
        component.getModel().attachContext(applicationContext)
    }

    override fun onTerminate() {
        super.onTerminate()
        component.getModel().detachContext()
    }
}