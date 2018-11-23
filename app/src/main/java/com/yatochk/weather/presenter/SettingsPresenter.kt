package com.yatochk.weather.presenter

import com.yatochk.weather.model.ModelContract
import com.yatochk.weather.view.settings.SettingsView

class SettingsPresenter(val model: ModelContract) {
    private var view: SettingsView? = null

    fun attachView(view: SettingsView) {
        this.view = view
        view.setDelaySeekbar(model.getDelayTime())
    }

    fun detachView() {
        if (view != null)
            model.setUpdateDelay(view!!.getTimeProgress())
        view = null
    }
}