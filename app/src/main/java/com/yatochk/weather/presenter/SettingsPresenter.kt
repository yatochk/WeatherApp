package com.yatochk.weather.presenter

import com.yatochk.weather.model.ModelContract
import com.yatochk.weather.view.settings.SettingsView

class SettingsPresenter(val model: ModelContract) {
    private var view: SettingsView? = null

    fun attachView(view: SettingsView) {
        this.view = view
        view.setDelaySeekBar(model.delayTime)
    }

    fun detachView() {
        if (view != null)
            model.setUpdateDelay(view!!.timeProgress)
        view = null
    }
}