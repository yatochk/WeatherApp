package com.yatochk.weather.model.onlineweather

import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.model.database.CityWeatherEntry


class OnlineWeather {
    fun getCityWeather(city: String, listener: (CityWeather) -> Unit) {
        listener(
            CityWeather(CityWeatherEntry.ID, city, "20")
        )
    }
}