package com.yatochk.weather.model

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.location.LocationManager
import com.yatochk.weather.model.database.*
import com.yatochk.weather.model.location.LocationTask
import com.yatochk.weather.model.onlineweather.OnlineWeather

class Model(private val onlineWeather: OnlineWeather) : ModelContract {
    private var locationManager: LocationManager? = null
    private var contentResolver: ContentResolver? = null
    private var context: Context? = null
    override fun attachContext(context: Context) {
        this.context = context
        contentResolver = context.contentResolver
        locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun detachContext() {
        context = null
    }

    override fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit) {
        if (context != null) {
            val getTask = GetCitiesWeatherTask(contentResolver!!)
            getTask.setOnGetCitiesWeatherListener(listener)
            getTask.execute()
        } else
            throw IllegalArgumentException("Context not attached")
    }

    override fun addCitiesWeather(city: String, listener: ((CityWeather) -> Unit)?) {
        if (context != null) {
            onlineWeather.getCityWeather(city) { cityWeather ->
                val values = ContentValues().apply {
                    put(CityWeatherEntry.CITY, cityWeather.city)
                    put(CityWeatherEntry.TEMPERATURE, cityWeather.temperature)
                }

                val addTask = AddCityWeatherTask(contentResolver!!, values)
                if (listener != null)
                    addTask.setOnAddCityWeatherListener(listener)
                addTask.execute()
            }
        } else
            throw IllegalArgumentException("Context not attached")
    }

    override fun deleteCitiesWeather(rowId: String, listener: (String) -> Unit) {
        if (context != null) {
            val deleteTask = DeleteCityWeatherTask(contentResolver!!, rowId)
            deleteTask.setOnDeleteCityWeatherListener(listener)
            deleteTask.execute()
        } else
            throw IllegalArgumentException("Context not attached")
    }

    override fun getCity(activity: Activity, listener: (String) -> Unit) {
        val locationTask = LocationTask(activity)
        locationTask.setLocationListener(listener)
    }
}