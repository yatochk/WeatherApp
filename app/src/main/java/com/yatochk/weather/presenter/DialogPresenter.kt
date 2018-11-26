package com.yatochk.weather.presenter

import android.app.Activity
import com.yatochk.weather.model.Model
import com.yatochk.weather.model.ModelContract
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.view.cities.AddCityDialog

class DialogPresenter(val model: ModelContract) {
    private var dialog: AddCityDialog? = null
    private var onAddCityListener: OnAddCityListener? = null
    fun attachDialog(dialog: AddCityDialog, onAddCityListener: OnAddCityListener?) {
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
                onAddCityListener?.onComplete()
            }

            override fun onError(code: Int) {
                onAddCityListener?.onError(code)
            }
        })
    }

    interface OnAddCityListener {
        fun onComplete()
        fun onError(code: Int)
    }
}