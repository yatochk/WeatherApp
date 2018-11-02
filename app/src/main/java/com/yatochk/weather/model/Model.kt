package com.yatochk.weather.model

import android.content.ContentResolver
import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.model.database.GetCitiesWeatherTask
import com.yatochk.weather.model.onlineweather.OnlineWeather

class Model(onlineWeather: OnlineWeather) : ModelContract {
    private var resolver: ContentResolver? = null
    override fun attachResolver(contentResolver: ContentResolver) {
        resolver = contentResolver
    }

    override fun getCitiesWeather(listener: (citiesWeather: ArrayList<CityWeather>) -> Unit) {
        if (resolver != null) {
            val getTask = GetCitiesWeatherTask(resolver!!)
            getTask.setOnGetCitiesWeatherListener(listener)
            getTask.execute()
        } else
            throw IllegalArgumentException("ContentResolver not attached")
    }
}