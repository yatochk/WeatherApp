package com.yatochk.weather.model

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import com.yatochk.weather.model.database.*
import com.yatochk.weather.model.location.LocationTask
import com.yatochk.weather.model.onlineweather.OnlineWeather

const val SETTINGS_PREFERENCES = "setPref"
const val UPDATE_DELAY_SETTINGS = "upDelPref"
const val DEFAULT_DELAY = 24

class Model(val context: Context) : ModelContract {
    companion object {
        private var contentResolver: ContentResolver? = null
    }

    init {
        contentResolver = context.contentResolver
    }

    override fun getCitiesWeather(listener: ((ArrayList<CityWeather>) -> Unit)?) {
        val getTask = GetCitiesWeatherTask(contentResolver!!)
        getTask.setOnGetCitiesWeatherListener(listener)
        getTask.start()
    }

    override fun updateCitiesWeathers(cities: ArrayList<CityWeather>, listener: OnGetUpdatedTaskListener?) {
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
                    if (city == cities.last())
                        updateTask.setOnUpdateCityWeatherListener {
                            getCitiesWeather { cities ->
                                listener?.onComplete(cities)
                            }
                        }
                    updateTask.start()
                }

                override fun onError(code: Int) {
                    listener?.onError(code)
                }

            })
        }
    }

    override fun addCityWeather(city: String, listener: OnAddTaskListener) {
        OnlineWeather.getCityWeather(city, object : OnlineWeather.OnWeatherTaskListener {
            override fun onComplete(temp: Int, json: String) {
                val values = ContentValues().apply {
                    put(CityWeatherEntry.CITY, city)
                    put(CityWeatherEntry.TEMPERATURE, temp)
                    put(CityWeatherEntry.FILE_NAME, json)
                }

                val addTask = AddCityWeatherTask(contentResolver!!, values)
                addTask.setOnAddCityWeatherListener(listener::onComplete)
                addTask.start()
            }

            override fun onError(code: Int) {
                listener.onError(code)
            }
        })
    }

    override fun deleteCitiesWeather(rowId: String, listener: ((String) -> Unit)?) {
        val deleteTask = DeleteCityWeatherTask(contentResolver!!, rowId)
        if (listener != null)
            deleteTask.setOnDeleteCityWeatherListener(listener)
        deleteTask.execute()
    }

    override fun getCity(activity: Activity, listener: (String) -> Unit) {
        val locationTask = LocationTask(activity)
        locationTask.setLocationListener(listener)
    }

    override val delayTime: Int
        get() = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE).getString(
            UPDATE_DELAY_SETTINGS, DEFAULT_DELAY.toString()
        ).toInt()

    override fun startUpdateService() {
        val serviceIntent = Intent(context, UpdateService::class.java)
        val time = delayTime
        serviceIntent.putExtra("time", time)
        context.startService(serviceIntent)
    }

    private fun startUpdateService(updateTime: Int) {
        val serviceIntent = Intent(context, UpdateService::class.java).apply {
            putExtra("time", updateTime)
        }

        context.startService(serviceIntent)
    }

    override fun setUpdateDelay(time: Int) {
        context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE).edit()
            .putString(UPDATE_DELAY_SETTINGS, time.toString())
            .apply()

        startUpdateService(time)
    }

    fun updateAllWeathers() {
        getCitiesWeather {
            updateCitiesWeathers(it, null)
        }
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

