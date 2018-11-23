package com.yatochk.weather.view.detailedweather

import com.yatochk.weather.model.database.CityWeather
import com.yatochk.weather.view.View

interface DetailedWeatherView : View {
    fun setWeather(cityWeather: CityWeather)
}