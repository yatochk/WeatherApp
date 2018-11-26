package com.yatochk.weather.model.onlineweather

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getCurrentWeather(@Query("q") cityName: String, @Query("appid") token: String): Call<CurrentWeatherModel>

    @GET("forecast/daily")
    fun getDailyWeather(@Query("q") cityName: String, @Query("appid") token: String): Call<ArrayList<DailyWeatherModel>>
}