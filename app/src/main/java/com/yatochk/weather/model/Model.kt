package com.yatochk.weather.model

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import com.yatochk.weather.model.database.*
import com.yatochk.weather.model.location.LocationTask
import com.yatochk.weather.model.onlineweather.CONNECTION_ERROR
import com.yatochk.weather.model.onlineweather.CurrentWeatherModel
import com.yatochk.weather.model.onlineweather.JSON_ERROR
import com.yatochk.weather.model.onlineweather.OnlineWeather
import retrofit2.Call
import retrofit2.Response

const val SETTINGS_PREFERENCES = "setPref"
const val UPDATE_DELAY_SETTINGS = "upDelPref"
const val DEFAULT_DELAY = 24

class Model(val context: Context) : ModelContract {
    companion object {
        private const val API_KEY = "825e609fb616ab086a97989cb0dc581b"
    }

    private val contentResolver = context.contentResolver

    override fun getCitiesWeather(listener: ((ArrayList<CityWeather>) -> Unit)?) {
        val getTask = GetCitiesWeatherTask(contentResolver)
        getTask.setOnGetCitiesWeatherListener(listener)
        getTask.start()
    }

    override fun updateCitiesWeathers(cities: ArrayList<CityWeather>, listener: OnGetUpdatedTaskListener?) {
        for (city in cities) {
            OnlineWeather.weatherApi.getCurrentWeather(city.city, API_KEY)
                .enqueue(object : retrofit2.Callback<CurrentWeatherModel> {
                    override fun onFailure(call: Call<CurrentWeatherModel>?, t: Throwable?) {
                        listener?.onError(CONNECTION_ERROR)
                    }

                    override fun onResponse(
                        call: Call<CurrentWeatherModel>?,
                        response: Response<CurrentWeatherModel>?
                    ) {
                        if (response != null) {
                            context.openFileOutput(city.city, Context.MODE_PRIVATE).use { fileOutputStream ->
                                fileOutputStream.write(response.body().toString().toByteArray())
                            }

                            val values = ContentValues().apply {
                                put(CityWeatherEntry.CITY, city.city)
                                put(CityWeatherEntry.TEMPERATURE, (response.body().main.temp - 273).toInt())
                                put(CityWeatherEntry.FILE_NAME, city.city)
                            }

                            val updateTask = UpdateCityWeatherTask(contentResolver, city.id, values)
                            if (city == cities[cities.size - 1])
                                updateTask.setOnUpdateCityWeatherListener {
                                    getCitiesWeather { cities ->
                                        listener?.onComplete(cities)
                                    }
                                }
                            updateTask.start()
                        }

                    }
                })
        }
    }

    override fun addCityWeather(city: String, listener: OnAddTaskListener) {
        OnlineWeather.weatherApi.getCurrentWeather(city, API_KEY)
            .enqueue(object : retrofit2.Callback<CurrentWeatherModel> {
                override fun onFailure(call: Call<CurrentWeatherModel>?, t: Throwable?) {
                    listener.onError(CONNECTION_ERROR)
                }

                override fun onResponse(call: Call<CurrentWeatherModel>?, response: Response<CurrentWeatherModel>?) {
                    if (response?.body() == null) {
                        listener.onError(JSON_ERROR)
                        return
                    }

                    Log.d("testedTags", response.body().toString())
                val values = ContentValues().apply {
                    put(CityWeatherEntry.CITY, city)
                    put(CityWeatherEntry.TEMPERATURE, (response.body().main.temp - 273).toInt())
                    put(CityWeatherEntry.FILE_NAME, "")
                }

                    val addTask = AddCityWeatherTask(contentResolver, values)
                addTask.setOnAddCityWeatherListener {
                    listener.onComplete(it)
                }
                addTask.start()

            }
        })
    }

    override fun deleteCitiesWeather(rowId: String, listener: ((String) -> Unit)?) {
        val deleteTask = DeleteCityWeatherTask(contentResolver, rowId)
        if (listener != null)
            deleteTask.setOnDeleteCityWeatherListener(listener)
        deleteTask.execute()
    }

    override fun getCity(activity: Activity, listener: (String) -> Unit) {
        val locationTask = LocationTask(activity)
        locationTask.setLocationListener(listener)
    }

    override fun getDelayTime(): Int =
        context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE).getInt(
            UPDATE_DELAY_SETTINGS, DEFAULT_DELAY
        )

    override fun startUpdateService() {
        val serviceIntent = Intent(context, UpdateService::class.java)
        val time = getDelayTime()
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
            .putInt(UPDATE_DELAY_SETTINGS, time)
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

