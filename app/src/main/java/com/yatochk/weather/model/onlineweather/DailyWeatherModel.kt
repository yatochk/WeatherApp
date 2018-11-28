package com.yatochk.weather.model.onlineweather

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.orm.SugarRecord


class DailyWeatherModel(
    @SerializedName("list")
    @Expose
    val list: List<Weathers>
) : SugarRecord<DailyWeatherModel>()

class Weathers(
    @SerializedName("dt")
    @Expose
    val dt: Int,
    @SerializedName("temp")
    @Expose
    val temp: Temp,
    @SerializedName("pressure")
    @Expose
    val pressure: Double,
    @SerializedName("humidity")
    @Expose
    val humidity: Int,
    @SerializedName("weather")
    @Expose
    val weather: List<DailyWeather>,
    @SerializedName("speed")
    @Expose
    val speed: Double,
    @SerializedName("deg")
    @Expose
    val deg: Int,
    @SerializedName("clouds")
    @Expose
    val clouds: Int,
    @SerializedName("rain")
    @Expose
    val rain: Double,
    @SerializedName("snow")
    @Expose
    val snow: Double
)

data class Temp(
    @SerializedName("day")
    @Expose
    val day: Double,
    @SerializedName("min")
    @Expose
    val min: Double,
    @SerializedName("max")
    @Expose
    val max: Double,
    @SerializedName("night")
    @Expose
    val night: Double,
    @SerializedName("eve")
    @Expose
    val eve: Double,
    @SerializedName("morn")
    @Expose
    val morn: Double
)

data class DailyWeather(
    @SerializedName("id")
    @Expose
    val id: Int,
    @SerializedName("main")
    @Expose
    val main: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("icon")
    @Expose
    val icon: String
)