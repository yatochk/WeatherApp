package com.yatochk.weather.model.database

import android.content.ContentResolver
import android.net.Uri
import android.provider.BaseColumns

const val CONTENT_AUTHORITY = "com.yatochk.weather.provider"
val BASE_CONTENT_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")
const val PATH_CITIES_WEATHER = "citiesWeather"

object CityWeatherEntry {
    const val TABLE_NAME = PATH_CITIES_WEATHER
    const val ID = BaseColumns._ID
    const val CITY = "city"
    const val TEMPERATURE = "temp"
    const val FILE_NAME = "file"

    val CONTENT_URI: Uri = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CITIES_WEATHER)
    const val CONTENT_LIST_TYPE =
        ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_WEATHER
    const val CONTENT_ITEM_TYPE =
        ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CITIES_WEATHER
}

data class CityWeather(val id: String, val city: String, val temperature: String, val weatherJsonName: String)