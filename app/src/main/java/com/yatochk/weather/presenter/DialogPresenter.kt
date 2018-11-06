package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.cities.AddCityDialog

class DialogPresenter(val model: Model) {
    private var dialog: AddCityDialog? = null
    fun attachDialog(dialog: AddCityDialog) {
        this.dialog = dialog
        model.attachContentResolver(dialog.activity.contentResolver)
        model.getCity(dialog.activity) { cityName ->
            dialog.setCity(cityName)
        }
    }

    fun detachDialog() {
        dialog = null
        model.detachContentResolver()
    }

    fun setClick(cityName: String) {
        model.addCitiesWeather(cityName, null)
        closeClick()
    }

    fun closeClick() {
        dialog?.closeDialog()
    }
}