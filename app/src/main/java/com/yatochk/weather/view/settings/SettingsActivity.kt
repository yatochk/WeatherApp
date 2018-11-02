package com.yatochk.weather.view.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yatochk.weather.R

class SettingsActivity : AppCompatActivity(), SettingsView {
    override val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }
}
