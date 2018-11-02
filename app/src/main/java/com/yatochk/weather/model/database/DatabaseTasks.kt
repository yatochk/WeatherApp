package com.yatochk.weather.model.database

import android.content.ContentResolver
import android.database.Cursor
import android.os.AsyncTask

class GetCitiesWeatherTask(private val contentResolver: ContentResolver) :
    AsyncTask<Unit, Unit, ArrayList<CityWeather>>() {
    private var onGetCitiesWeatherListener: ((citiesWeather: ArrayList<CityWeather>) -> Unit)? = null
    fun setOnGetCitiesWeatherListener(listener: (citiesWeather: ArrayList<CityWeather>) -> Unit) {
        onGetCitiesWeatherListener = listener
    }

    override fun doInBackground(vararg params: Unit?): ArrayList<CityWeather> {
        val citiesWeather = ArrayList<CityWeather>()

        val projection = arrayOf(CityWeatherEntry.ID, CityWeatherEntry.CITY, CityWeatherEntry.TEMPERATURE)
        val cursor: Cursor?
        cursor = contentResolver.query(
            CityWeatherEntry.CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val rowId = getString(getColumnIndexOrThrow(CityWeatherEntry.ID))
                val cityName = getString(getColumnIndexOrThrow(CityWeatherEntry.CITY))
                val cityTemp = getString(getColumnIndexOrThrow(CityWeatherEntry.TEMPERATURE))

                citiesWeather.add(
                    CityWeather(
                        rowId,
                        cityName,
                        cityTemp
                    )
                )
            }
        }

        return citiesWeather
    }

    override fun onPostExecute(result: ArrayList<CityWeather>?) {
        super.onPostExecute(result)

        if (result != null)
            onGetCitiesWeatherListener?.invoke(result)
    }
}