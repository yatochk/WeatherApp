package com.yatochk.weather.view.settings

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.yatochk.weather.R

class SettingsActivity : AppCompatActivity(), SettingsView {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
