package com.yatochk.weather.model

import android.app.Activity
import com.yatochk.weather.model.database.CityWeather

interface ModelContract {
    fun getCitiesWeather(listener: ((ArrayList<CityWeather>) -> Unit)?)
    fun updateCitiesWeathers(cities: ArrayList<CityWeather>, listener: Model.OnGetUpdatedTaskListener?)
    fun addCityWeather(city: String, listener: Model.OnAddTaskListener)
    fun deleteCitiesWeather(rowId: String, listener: ((String) -> Unit)?)
    fun getCity(activity: Activity, listener: (String) -> Unit)
    fun setUpdateDelay(time: Int)
    fun startUpdateService()
    fun getDelayTime(): Int
}