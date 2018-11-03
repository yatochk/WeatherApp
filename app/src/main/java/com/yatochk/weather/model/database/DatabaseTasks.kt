package com.yatochk.weather.model.database

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.os.AsyncTask

class GetCitiesWeatherTask(private val contentResolver: ContentResolver) :
    AsyncTask<Unit, Unit, ArrayList<CityWeather>>() {
    private var onGetCitiesWeatherListener: ((ArrayList<CityWeather>) -> Unit)? = null
    fun setOnGetCitiesWeatherListener(listener: (ArrayList<CityWeather>) -> Unit) {
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

class AddCityWeatherTask(private val contentResolver: ContentResolver, val values: ContentValues) :
    AsyncTask<Unit, Unit, CityWeather>() {
    private var onAddCityWeatherListener: ((CityWeather) -> Unit)? = null
    fun setOnAddCityWeatherListener(listener: (CityWeather) -> Unit) {
        onAddCityWeatherListener = listener
    }

    override fun doInBackground(vararg params: Unit?): CityWeather {
        val rowId = contentResolver.insert(CityWeatherEntry.CONTENT_URI, values)

        return CityWeather(
            rowId.toString().substring(rowId.toString().lastIndexOf('/') + 1, rowId.toString().length),
            values.getAsString(CityWeatherEntry.CITY),
            values.getAsString(CityWeatherEntry.TEMPERATURE)
        )
    }

    override fun onPostExecute(result: CityWeather?) {
        super.onPostExecute(result)

        if (result != null)
            onAddCityWeatherListener?.invoke(result)
    }
}

class DeleteCityWeatherTask(private val contentResolver: ContentResolver, private val rowId: String) :
    AsyncTask<Unit, Unit, Int>() {
    private var onDeleteCityWeatherListener: ((String) -> Unit)? = null
    fun setOnDeleteCityWeatherListener(listener: (String) -> Unit) {
        onDeleteCityWeatherListener = listener
    }

    override fun doInBackground(vararg params: Unit?): Int {
        return contentResolver.delete(CityWeatherEntry.CONTENT_URI, CityWeatherEntry.ID, arrayOf(rowId))
    }

    override fun onPostExecute(result: Int?) {
        super.onPostExecute(result)

        if (result != null)
            onDeleteCityWeatherListener?.invoke(result.toString())
    }
}