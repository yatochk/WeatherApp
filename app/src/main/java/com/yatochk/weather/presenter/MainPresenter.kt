package com.yatochk.weather.presenter

import com.yatochk.weather.model.Model
import com.yatochk.weather.model.ModelContract
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.model.onlineweather.CONNECTION_ERROR
import com.yatochk.weather.model.onlineweather.JSON_ERROR
import com.yatochk.weather.view.cities.CitiesView

class MainPresenter(val model: ModelContract) {
    private var citiesView: CitiesView? = null

    private val connectionErrorMsg = "Please check internet connection"
    private val unknownErrorMsg = "Unknown error"
    private val onlineServiceErrorMsg = "Weather service error"

    fun attachView(view: CitiesView) {
        citiesView = view
        model.getCitiesWeather {
            if (it.size > 0)
                citiesView?.updateCitiesRecycler(it)
            else
                citiesView?.openLocationDialog()
        }

        model.startUpdateService()
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
        model.updateCitiesWeathers(cities, object : Model.OnGetUpdatedTaskListener {
            override fun onComplete(cities: ArrayList<CityWeather>) {
                citiesView?.updateCitiesRecycler(cities)
                citiesView?.stopUpdateAnim()
            }

            override fun onError(code: Int) {
                citiesView?.showMessage(
                    when (code) {
                        CONNECTION_ERROR -> connectionErrorMsg
                        JSON_ERROR -> onlineServiceErrorMsg
                        else -> unknownErrorMsg
                    }
                )
                citiesView?.stopUpdateAnim()
            }
        })
    }

    fun swipeDeleteCity(deleteCityId: String) {
        model.deleteCitiesWeather(deleteCityId) {}
    }

    fun clickCity() {
        citiesView?.openWeatherActivity()
    }
}