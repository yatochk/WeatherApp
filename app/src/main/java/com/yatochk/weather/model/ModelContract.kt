package com.yatochk.weather.model

import android.app.Activity
import android.content.Context
import com.yatochk.weather.model.database.CityWeather

interface ModelContract {
    fun attachContext(context: Context)
    fun detachContext()
    fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit)
    fun addCitiesWeather(city: String, listener: ((CityWeather) -> Unit)?)
    fun deleteCitiesWeather(rowId: String, listener: (String) -> Unit)
    fun getCity(activity: Activity, listener: (String) -> Unit)
}