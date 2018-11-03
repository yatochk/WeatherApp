package com.yatochk.weather.model

import android.content.ContentResolver
import android.content.ContentValues
import com.yatochk.weather.model.database.CityWeather

interface ModelContract {
    fun attachResolver(contentResolver: ContentResolver)
    fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit)
    fun addCitiesWeather(values: ContentValues, listener: (CityWeather) -> Unit)
    fun deleteCitiesWeather(rowId: String, listener: (String) -> Unit)
}