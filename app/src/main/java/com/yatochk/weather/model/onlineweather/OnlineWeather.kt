package com.yatochk.weather.model.onlineweather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val SUCCESS_TASK = 100
const val CONNECTION_ERROR = 101
const val JSON_ERROR = 102
const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

object OnlineWeather {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val weatherApi: WeatherApi

    init {
        weatherApi = retrofit.create(WeatherApi::class.java)
    }
}