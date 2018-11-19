package com.yatochk.weather.model

import android.app.Activity
import android.content.Context
import com.yatochk.weather.model.database.CityWeather

interface ModelContract {
    fun attachContext(context: Context)
    fun detachContext()
    fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit)
    fun getUpdatedCitiesWeather(cities: ArrayList<CityWeather>, listener: Model.OnGetUpdatedTaskListener)
    fun addCityWeather(city: String, listener: Model.OnAddTaskListener)
    fun deleteCitiesWeather(rowId: String, listener: ((String) -> Unit)?)
    fun updateCitiesWeather(rowId: String, cityWeather: CityWeather, listener: (String) -> Unit)
    fun getCity(activity: Activity, listener: (String) -> Unit)
    fun setUpdateDelay(time: Long)
}