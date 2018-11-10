package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.cities.AddCityDialog
import com.yatochk.weather.view.cities.AddCityView

class DialogPresenter(val model: Model) {
    private var dialog: AddCityDialog? = null
    private var addCityView: AddCityView? = null
    fun attachDialog(dialog: AddCityDialog) {
        this.dialog = dialog
        model.getCity(dialog.activity) { cityName ->
            dialog.setCity(cityName)
        }
    }

    fun detachDialog() {
        dialog = null
    }

    fun setClick(cityName: String) {
        model.addCityWeather(cityName) {
            dialog?.closeDialog()
        }
    }

    fun closeClick() {
        addCityView?.closeDialog()
    }
}