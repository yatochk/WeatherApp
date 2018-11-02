package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.settings.SettingsView

class SettingsPresenter(val model: Model) {
    private var view: SettingsView? = null

    fun attachView(view: SettingsView) {
        this.view = view
        model.attachResolver(view.context.contentResolver)
    }

    fun detachView() {
        view = null
    }
}