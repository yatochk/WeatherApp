package com.yatochk.weather.view.cities

import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.view.View

interface CitiesView : View {
    fun updateCitiesRecycler(cities: ArrayList<CityWeather>)
    fun openLocationDialog()
    fun openWeatherActivity()
    fun stopUpdateAnim()
    fun showMessage(msg: String)
    fun openSettings()
}