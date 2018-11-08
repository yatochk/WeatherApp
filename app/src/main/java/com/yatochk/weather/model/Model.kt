package com.yatochk.weather.model

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.util.Log
import com.yatochk.weather.model.database.*
import com.yatochk.weather.model.location.LocationTask
import com.yatochk.weather.model.onlineweather.OnlineWeather

class Model : ModelContract {
    companion object {
        private var contentResolver: ContentResolver? = null
    }

    override fun attachContentResolver(contentResolver: ContentResolver) {
        Model.contentResolver = contentResolver
        Log.i("ModelInfo", "ContentResolver attached")
    }

    override fun detachContentResolver() {
        contentResolver = null
        Log.i("ModelInfo", "ContentResolver detached")
    }

    override fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")
        val getTask = GetCitiesWeatherTask(contentResolver!!)
        getTask.setOnGetCitiesWeatherListener(listener)
        getTask.execute()
    }

    override fun addCitiesWeather(city: String, listener: ((CityWeather) -> Unit)?) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        OnlineWeather.getCityWeather(city) { temp, fileName ->
            val values = ContentValues().apply {
                put(CityWeatherEntry.CITY, city)
                put(CityWeatherEntry.TEMPERATURE, temp)
                put(CityWeatherEntry.FILE_NAME, fileName)
            }

            val addTask = AddCityWeatherTask(contentResolver!!, values)
            if (listener != null)
                addTask.setOnAddCityWeatherListener(listener)
            addTask.execute()
        }
    }

    override fun updateCitiesWeather(rowId: String, cityWeather: CityWeather, listener: (String) -> Unit) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        val values = ContentValues().apply {
            put(CityWeatherEntry.CITY, cityWeather.city)
            put(CityWeatherEntry.TEMPERATURE, cityWeather.temperature)
        }
        val updateCityWeatherTask = UpdateCityWeatherTask(contentResolver!!, rowId, values)
        updateCityWeatherTask.setOnDeleteCityWeatherListener(listener)
    }

    override fun deleteCitiesWeather(rowId: String, listener: (String) -> Unit) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        val deleteTask = DeleteCityWeatherTask(contentResolver!!, rowId)
        deleteTask.setOnDeleteCityWeatherListener(listener)
        deleteTask.execute()
    }

    override fun getCity(activity: Activity, listener: (String) -> Unit) {
        val locationTask = LocationTask(activity)
        locationTask.setLocationListener(listener)
    }
}