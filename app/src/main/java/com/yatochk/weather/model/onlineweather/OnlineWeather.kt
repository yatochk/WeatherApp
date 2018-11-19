package com.yatochk.weather.model.onlineweather

import android.os.Handler
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val SUCCESS_TASK = 100
const val CONNECTION_ERROR = 101
const val JSON_ERROR = 102

object OnlineWeather {
    private const val API_KEY = "825e609fb616ab086a97989cb0dc581b"

    fun getCityWeather(cityName: String, listener: OnWeatherTaskListener) {
        val url = "http://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY"
        WeatherTask(url) { jsonString, code ->
            if (code == CONNECTION_ERROR) {
                listener.onError(code)
                return@WeatherTask
            }

            try {
                val jsonObject = JSONObject(jsonString)
                val temp = jsonObject.getJSONObject("main").getDouble("temp") - 273.15
                listener.onComplete(temp.toInt(), jsonString)
            } catch (e: JSONException) {
                listener.onError(JSON_ERROR)
            }
        }.startTask()
    }

    private class WeatherTask(urlString: String, listener: (String, Int) -> Unit) {
        private val handler = Handler()

        private val taskThread = Thread {
            var result = ""
            var code = SUCCESS_TASK
            try {
                val url = URL(urlString)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                result = reader.readLine()

            } catch (e: Exception) {
                code = CONNECTION_ERROR
            }

            handler.post {
                listener(result, code)
            }
        }

        fun startTask() = taskThread.start()
    }

    interface OnWeatherTaskListener {
        fun onComplete(temp: Int, json: String)
        fun onError(code: Int)
    }
}