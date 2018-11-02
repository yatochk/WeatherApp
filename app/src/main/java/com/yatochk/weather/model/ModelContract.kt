package com.yatochk.weather.model

import android.content.ContentResolver
import com.yatochk.weather.model.database.CityWeather

interface ModelContract {
    fun attachResolver(contentResolver: ContentResolver)
    fun getCitiesWeather(listener: (citiesWeather: ArrayList<CityWeather>) -> Unit)
}