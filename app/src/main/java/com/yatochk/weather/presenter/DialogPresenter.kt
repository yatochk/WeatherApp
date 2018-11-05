package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.cities.AddCityDialog

class DialogPresenter(val model: Model) {
    private var dialog: AddCityDialog? = null
    fun attachDialog(dialog: AddCityDialog) {
        this.dialog = dialog
        model.attachContext(dialog.activity)
        model.getCity(dialog.activity) { cityName ->
            dialog.setCity(cityName)
        }
    }

    fun detachDialog() {
        dialog = null
        model.detachContext()
    }

    fun setClick(cityName: String) {
        model.addCitiesWeather(cityName, null)
        dialog?.closeDialog()
    }

    fun closeClick() {
        dialog?.closeDialog()
    }
}