package com.yatochk.weather.view.settings

import com.yatochk.weather.view.View

interface SettingsView : View {
    fun getTimeProgress(): Int
    fun setDelaySeekbar(time: Int)
}