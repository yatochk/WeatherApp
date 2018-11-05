package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.cities.AddCityDialog
import com.yatochk.weather.view.cities.CitiesView

class MainPresenter(val model: Model) {
    private var citiesView: CitiesView? = null

    fun attachView(view: CitiesView) {
        citiesView = view
        model.attachContext(citiesView!!.activity)
        model.getCitiesWeather { citiesWeather ->
            if (citiesWeather.size > 0)
                citiesView!!.updateCitiesRecycler(citiesWeather)
            else
                AddCityDialog()
        }
    }

    fun detachView() {
        citiesView = null
        model.detachContext()
    }

    fun clickSearchCity() {

    }

    fun clickAddCity() {
        if (citiesView != null) {
            val addCityDialog = AddCityDialog()
            addCityDialog.show(citiesView!!.activity.fragmentManager, "Add")
            addCityDialog.setOnCancelListener {
                model.getCitiesWeather { citiesWeather ->
                    if (citiesWeather.size > 0)
                        citiesView!!.updateCitiesRecycler(citiesWeather)
                }
            }
        }
    }

    fun clickDeleteCity(deleteCityId: String) {

    }

    fun clickCity() {

    }
}