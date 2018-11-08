package com.yatochk.weather.model.onlineweather

import android.os.Handler
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

const val CONNECTION_ERROR = 101
const val API_KEY = "825e609fb616ab086a97989cb0dc581b"

object OnlineWeather {
    fun getCityWeather(cityName: String, listener: (temp: String, fileName: String) -> Unit) {
        val url = "http://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=$API_KEY"
        WeatherTask(url) {
            val jsonObject = JSONObject(it)
            val temp = jsonObject.getJSONObject("main").getDouble("temp") - 273.15

            

            listener(temp.toInt().toString(), "hi")
        }.startTask()
    }

    private class WeatherTask(urlString: String, listener: (String) -> Unit) {
        private val handler = Handler()

        private val taskThread = Thread {
            var result: String
            try {
                val url = URL(urlString)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.connect()

                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                result = reader.readLine()

            } catch (e: Exception) {
                Log.i("ERRORINET", "ERROR")
                result = "{ \"cod\": $CONNECTION_ERROR}"
            }

            handler.post {
                listener(result)
            }
        }

        fun startTask() = taskThread.start()
    }
}