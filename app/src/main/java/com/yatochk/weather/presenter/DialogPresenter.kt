package com.yatochk.weather.presenter

import android.app.Activity
import com.yatochk.weather.model.Model
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.view.cities.AddCityDialog

class DialogPresenter(val model: Model) {
    private var dialog: AddCityDialog? = null
    private var onAddCityListener: (() -> Unit)? = null
    fun attachDialog(dialog: AddCityDialog, onAddCityListener: (() -> Unit)?) {
        this.dialog = dialog
        this.onAddCityListener = onAddCityListener
        model.getCity(dialog.activity as Activity) { cityName ->
            dialog.setCity(cityName)
        }
    }

    fun detachDialog() {
        dialog = null
    }

    fun setClick(cityName: String) {
        model.addCityWeather(cityName, object : Model.OnAddTaskListener {
            override fun onComplete(cityWeather: CityWeather) {
                onAddCityListener?.invoke()
            }

            override fun onError(code: Int) {

            }
        })
    }
}