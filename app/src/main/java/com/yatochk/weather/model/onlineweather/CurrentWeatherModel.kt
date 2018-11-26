package com.yatochk.weather.model.onlineweather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrentWeatherModel(
    @SerializedName("weather")
    @Expose
    val weather: List<Weather>,
    @SerializedName("main")
    @Expose
    val main: Main,
    @SerializedName("wind")
    @Expose
    val wind: Wind,
    @SerializedName("rain")
    @Expose
    val rain: Rain,
    @SerializedName("clouds")
    @Expose
    val clouds: Clouds
)

class Clouds {
    @SerializedName("all")
    @Expose
    var all: Int? = null
}

data class Main(
    @SerializedName("temp")
    @Expose
    var temp: Double,
    @SerializedName("humidity")
    @Expose
    var humidity: Int,
    @SerializedName("pressure")
    @Expose
    var pressure: Int,
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double,
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double
)

data class Rain(
    @SerializedName("3h")
    @Expose
    var hour: Int
)

data class Weather(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("main")
    @Expose
    var main: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("icon")
    @Expose
    var icon: String
)

data class Wind(
    @SerializedName("speed")
    @Expose
    val speed: Double,
    @SerializedName("deg")
    @Expose
    val deg: Double
)