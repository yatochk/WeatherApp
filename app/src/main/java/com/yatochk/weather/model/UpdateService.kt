package com.yatochk.weather.model

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.yatochk.weather.dagger.App
import java.util.*

const val NO_TIME = -1
const val MS_PER_MINUTE = 3600L

class UpdateService : Service() {
    private val timer = Timer()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val time = intent?.getIntExtra("time", NO_TIME) ?: NO_TIME
        if (time != NO_TIME) {
            timer.scheduleAtFixedRate(
                object : TimerTask() {
                    override fun run() {
                        App.component.getModel().updateAllWeathers()
                    }
                },
                0, time * MS_PER_MINUTE
            )
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}