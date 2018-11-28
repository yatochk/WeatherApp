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
        model.getCurrentWeather {
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

    fun settingsClick() =
        citiesView?.openSettings()

    fun clickAddCity() =
        citiesView?.openLocationDialog()

    fun closeDialog() =
        model.getCurrentWeather {
            citiesView?.updateCitiesRecycler(it)
        }

    fun closeDialogWithError(errorCode: Int) = showError(errorCode)

    private fun showError(errorCode: Int) {
        citiesView?.showMessage(
            when (errorCode) {
                CONNECTION_ERROR -> connectionErrorMsg
                JSON_ERROR -> onlineServiceErrorMsg
                else -> unknownErrorMsg
            }
        )
    }

    fun updateWeatherSwipe(cities: ArrayList<CityWeather>) {
        model.updateCitiesWeathers(cities, object : Model.OnGetUpdatedTaskListener {
            override fun onComplete(cities: ArrayList<CityWeather>) {
                citiesView?.updateCitiesRecycler(cities)
                citiesView?.stopUpdateAnim()
            }

            override fun onError(code: Int) {
                showError(code)
                citiesView?.stopUpdateAnim()
            }
        })
    }

    fun swipeDeleteCity(deleteCityId: String) =
        model.deleteCitiesWeather(deleteCityId) {}

    fun clickCity() =
        citiesView?.openWeatherActivity()
}