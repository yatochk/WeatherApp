package com.yatochk.weather.view.settings

import com.yatochk.weather.view.View

interface SettingsView : View {
    val timeProgress: Int
    fun setDelaySeekBar(time: Int)
}