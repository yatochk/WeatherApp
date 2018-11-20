package com.yatochk.weather.view.settings

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import com.yatochk.weather.R
import com.yatochk.weather.dagger.App
import com.yatochk.weather.presenter.SettingsPresenter
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), SettingsView {
    private lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        presenter = App.component.getSettingsPresenter()

        time_seek_bar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                time.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })

    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun getTimeProgress(): Int {
        return time_seek_bar.progress
    }
}
