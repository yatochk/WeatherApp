package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.settings.SettingsView

class SettingsPresenter(val model: Model) {
    private var view: SettingsView? = null

    fun attachView(view: SettingsView) {
        this.view = view
    }

    fun detachView() {
        if (view != null)
            model.setUpdateDelay(view!!.getTimeProgress())
        view = null
    }
}