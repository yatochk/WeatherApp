package com.yatochk.weather.model

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import com.yatochk.weather.model.database.*
import com.yatochk.weather.model.location.LocationTask
import com.yatochk.weather.model.onlineweather.OnlineWeather

class Model(private val onlineWeather: OnlineWeather) : ModelContract {
    private var contentResolver: ContentResolver? = null
    private var context: Context? = null
    override fun attachContentResolver(contentResolver: ContentResolver) {
        this.contentResolver = contentResolver
    }

    override fun detachContentResolver() {
        context = null
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

        onlineWeather.getCityWeather(city) { temp, fileName ->
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