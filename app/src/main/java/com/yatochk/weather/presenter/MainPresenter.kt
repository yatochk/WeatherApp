package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.view.cities.CitiesView

class MainPresenter(val model: Model) {
    private var citiesView: CitiesView? = null

    fun attachView(view: CitiesView) {
        citiesView = view
        model.attachResolver(citiesView!!.context.contentResolver)
        model.getCitiesWeather {
            citiesView!!.updateCitiesRecycler(it)
        }
    }

    fun detachView() {
        citiesView = null
    }

    fun clickSearchCity() {

    }

    fun clickAddCity() {

    }

    fun clickAddCity(city: CityWeather) {

    }

    fun clickDeleteCity(deleteCityId: String) {

    }

    fun clickCity() {

    }
}