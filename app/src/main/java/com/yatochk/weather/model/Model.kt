package com.yatochk.weather.model

import android.content.ContentResolver
import android.content.ContentValues
import com.yatochk.weather.model.database.AddCityWeatherTask
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.model.database.DeleteCityWeatherTask
import com.yatochk.weather.model.database.GetCitiesWeatherTask
import com.yatochk.weather.model.onlineweather.OnlineWeather

class Model(onlineWeather: OnlineWeather) : ModelContract {
    private var resolver: ContentResolver? = null
    override fun attachResolver(contentResolver: ContentResolver) {
        resolver = contentResolver
    }

    override fun getCitiesWeather(listener: (ArrayList<CityWeather>) -> Unit) {
        if (resolver != null) {
            val getTask = GetCitiesWeatherTask(resolver!!)
            getTask.setOnGetCitiesWeatherListener(listener)
            getTask.execute()
        } else
            throw IllegalArgumentException("ContentResolver not attached")
    }

    override fun addCitiesWeather(values: ContentValues, listener: (CityWeather) -> Unit) {
        if (resolver != null) {
            val addTask = AddCityWeatherTask(resolver!!, values)
            addTask.setOnAddCityWeatherListener(listener)
            addTask.execute()
        } else
            throw IllegalArgumentException("ContentResolver not attached")
    }

    override fun deleteCitiesWeather(rowId: String, listener: (String) -> Unit) {
        if (resolver != null) {
            val deleteTask = DeleteCityWeatherTask(resolver!!, rowId)
            deleteTask.setOnDeleteCityWeatherListener(listener)
            deleteTask.execute()
        } else
            throw IllegalArgumentException("ContentResolver not attached")
    }
}