package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.view.cities.CitiesView

class MainPresenter(val model: Model) {
    private var citiesView: CitiesView? = null

    fun attachView(view: CitiesView) {
        citiesView = view
        model.getCitiesWeather {
            if (it.size > 0)
                citiesView?.updateCitiesRecycler(it)
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
        model.getCitiesWeather {
            citiesView?.updateCitiesRecycler(it)
        }
    }

    fun updateWeatherSwipe(cities: ArrayList<CityWeather>) {
        model.getUpdatedCitiesWeather(cities) {
            citiesView?.updateCitiesRecycler(it)
            citiesView?.stopUpateAnim()
        }
    }

    fun clickDeleteCity(deleteCityId: String) {

    }

    fun clickCity() {
        citiesView?.openWeatherActivity()
    }
}