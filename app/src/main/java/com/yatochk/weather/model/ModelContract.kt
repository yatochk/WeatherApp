package com.yatochk.weather.model

import android.app.Activity
import android.content.ContentResolver
import com.yatochk.weather.model.database.CityWeather

interface ModelContract {
    fun attachContentResolver(contentResolver: ContentResolver)
    fun detachContentResolver()
    fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit)
    fun addCitiesWeather(city: String, listener: ((CityWeather) -> Unit)?)
    fun deleteCitiesWeather(rowId: String, listener: (String) -> Unit)
    fun updateCitiesWeather(rowId: String, cityWeather: CityWeather, listener: (String) -> Unit)
    fun getCity(activity: Activity, listener: (String) -> Unit)
}