package com.yatochk.weather.presenter

import android.content.ContentResolver
import android.support.annotation.NonNull
import com.yatochk.weather.model.Model
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.view.cities.CitiesView

class MainPresenter(val model: Model) {
    private var view: CitiesView? = null
    private val citiesWeather = ArrayList<CityWeather>()

    fun attachView(@NonNull view: CitiesView) {
        this.view = view
        model.attachResolver(view.context.contentResolver)

        if (citiesWeather.size == 0) {
            model.getCitiesWeather { citiesWeather ->
                this.view!!.updateCitiesRecycler(citiesWeather)
            }
        }
    }

    fun detachView() {
        view = null
    }

    fun startApp(contentResolver: ContentResolver) {
        model.attachResolver(contentResolver)
        model.getCitiesWeather { citiesWeather ->
            this.citiesWeather.addAll(citiesWeather)
        }
    }

    fun clickSearchCity() {

    }

    fun clickAddCity() {

    }

    fun clickDeleteCity(deleteCityId: String) {

    }

    fun clickCity() {

    }
}