package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.view.cities.CitiesView

class MainPresenter(val model: Model) {
    private var citiesView: CitiesView? = null

    fun attachView(view: CitiesView) {
        citiesView = view
        model.getCitiesWeather { citiesWeather ->
            if (citiesWeather.size > 0)
                citiesView?.updateCitiesRecycler(citiesWeather)
            else
                citiesView?.openLocationDialog()
        }
    }

    fun detachView() {
        citiesView = null
    }

    fun clickAddCity() {
        citiesView?.openLocationDialog()
    }

    fun closeDialog() {
        model.getCitiesWeather { citiesWeather ->
            citiesView?.updateCitiesRecycler(citiesWeather)
        }
    }

    fun clickDeleteCity(deleteCityId: String) {

    }

    fun clickCity() {
        citiesView?.openWeatherActivity()
    }
}