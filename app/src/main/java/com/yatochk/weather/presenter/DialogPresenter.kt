package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.cities.AddCityDialog

class DialogPresenter(model: Model) {
    private var dialog: AddCityDialog? = null
    fun attachDialog(dialog: AddCityDialog) {
        this.dialog = dialog
    }

}