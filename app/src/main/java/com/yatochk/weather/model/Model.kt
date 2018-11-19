package com.yatochk.weather.model

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.yatochk.weather.model.database.*
import com.yatochk.weather.model.location.LocationTask
import com.yatochk.weather.model.onlineweather.OnlineWeather

const val SETTINGS_PREFERENCES = "setPref"
const val UPDATE_DELAY_SETTINGS = "upDelPref"

class Model : ModelContract {
    companion object {
        private lateinit var context: Context
        private var contentResolver: ContentResolver? = null
    }

    override fun attachContext(context: Context) {
        contentResolver = context.contentResolver
        Model.context = context
        Log.i("ModelInfo", "ContentResolver attached")
    }

    override fun detachContext() {
        contentResolver = null
        Log.i("ModelInfo", "ContentResolver detached")
    }

    override fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        val getTask = GetCitiesWeatherTask(contentResolver!!)
        getTask.setOnGetCitiesWeatherListener(listener)
        getTask.start()
    }

    override fun getUpdatedCitiesWeather(cities: ArrayList<CityWeather>, listener: OnGetUpdatedTaskListener) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        for (city in cities) {
            OnlineWeather.getCityWeather(city.city, object : OnlineWeather.OnWeatherTaskListener {
                override fun onComplete(temp: Int, json: String) {
                    context.openFileOutput(city.city, Context.MODE_PRIVATE).use { fileOutputStream ->
                        fileOutputStream.write(json.toByteArray())
                    }

                    val values = ContentValues().apply {
                        put(CityWeatherEntry.CITY, city.city)
                        put(CityWeatherEntry.TEMPERATURE, temp)
                        put(CityWeatherEntry.FILE_NAME, city.city)
                    }

                    val updateTask = UpdateCityWeatherTask(contentResolver!!, city.id, values)
                    if (city == cities[cities.size - 1])
                        updateTask.setOnUpdateCityWeatherListener {
                            getCitiesWeather { cities ->
                                listener.onComplete(cities)
                            }
                        }
                    updateTask.start()
                }

                override fun onError(code: Int) {
                    listener.onError(code)
                }

            })
        }
    }

    override fun addCityWeather(city: String, listener: OnAddTaskListener) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        OnlineWeather.getCityWeather(city, object : OnlineWeather.OnWeatherTaskListener {
            override fun onComplete(temp: Int, json: String) {
                val values = ContentValues().apply {
                    put(CityWeatherEntry.CITY, city)
                    put(CityWeatherEntry.TEMPERATURE, temp)
                    put(CityWeatherEntry.FILE_NAME, json)
                }

                val addTask = AddCityWeatherTask(contentResolver!!, values)
                addTask.setOnAddCityWeatherListener {
                    listener.onComplete(it)
                }
                addTask.start()
            }

            override fun onError(code: Int) {
                listener.onError(code)
            }
        })
    }

    override fun updateCitiesWeather(rowId: String, cityWeather: CityWeather, listener: (String) -> Unit) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        val values = ContentValues().apply {
            put(CityWeatherEntry.CITY, cityWeather.city)
            put(CityWeatherEntry.TEMPERATURE, cityWeather.temperature)
        }
        val updateCityWeatherTask = UpdateCityWeatherTask(contentResolver!!, rowId, values)
        updateCityWeatherTask.setOnUpdateCityWeatherListener(listener)
    }

    override fun deleteCitiesWeather(rowId: String, listener: ((String) -> Unit)?) {
        if (contentResolver == null)
            throw IllegalArgumentException("ContentResolve not attached")

        val deleteTask = DeleteCityWeatherTask(contentResolver!!, rowId)
        if (listener != null)
            deleteTask.setOnDeleteCityWeatherListener(listener)
        deleteTask.execute()
    }

    override fun getCity(activity: Activity, listener: (String) -> Unit) {
        val locationTask = LocationTask(activity)
        locationTask.setLocationListener(listener)
    }

    override fun setUpdateDelay(time: Long) {
        context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE).edit()
            .putString(UPDATE_DELAY_SETTINGS, time.toString())
            .apply()
    }

    interface OnGetUpdatedTaskListener {
        fun onComplete(cities: ArrayList<CityWeather>)
        fun onError(code: Int)
    }

    interface OnAddTaskListener {
        fun onComplete(cityWeather: CityWeather)
        fun onError(code: Int)
    }
}

