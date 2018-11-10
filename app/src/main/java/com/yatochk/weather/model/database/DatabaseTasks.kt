package com.yatochk.weather.model.database

import android.content.ContentResolver
import android.content.ContentValues
import android.database.Cursor
import android.os.AsyncTask
import android.os.Handler

class GetCitiesWeatherTask(private val contentResolver: ContentResolver) {
    private var onGetCitiesWeatherListener: ((ArrayList<CityWeather>) -> Unit)? = null
    fun setOnGetCitiesWeatherListener(listener: (ArrayList<CityWeather>) -> Unit) {
        onGetCitiesWeatherListener = listener
    }

    private val thread: Thread

    init {
        val handler = Handler()
        val citiesWeather = ArrayList<CityWeather>()

        thread = Thread {
            val projection = arrayOf(
                CityWeatherEntry.ID,
                CityWeatherEntry.CITY,
                CityWeatherEntry.TEMPERATURE,
                CityWeatherEntry.FILE_NAME
            )
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
                    val weatherFileName = getString(getColumnIndex(CityWeatherEntry.FILE_NAME))

                    citiesWeather.add(
                        CityWeather(
                            rowId,
                            cityName,
                            cityTemp,
                            weatherFileName
                        )
                    )
                }
            }

            handler.post {
                onGetCitiesWeatherListener?.invoke(citiesWeather)
            }
        }
    }

    fun start() {
        thread.start()
    }
}

class AddCityWeatherTask(contentResolver: ContentResolver, val values: ContentValues) {
    private var onAddCityWeatherListener: ((CityWeather) -> Unit)? = null
    fun setOnAddCityWeatherListener(listener: (CityWeather) -> Unit) {
        onAddCityWeatherListener = listener
    }

    private val thread: Thread

    init {
        val rowId = contentResolver.insert(CityWeatherEntry.CONTENT_URI, values)
        val handler = Handler()
        thread = Thread {
            val weather = CityWeather(
                rowId.toString().substring(rowId.toString().lastIndexOf('/') + 1, rowId.toString().length),
                values.getAsString(CityWeatherEntry.CITY),
                values.getAsString(CityWeatherEntry.TEMPERATURE),
                values.getAsString(CityWeatherEntry.FILE_NAME)
            )

            handler.post {
                onAddCityWeatherListener?.invoke(weather)
            }
        }
    }

    fun start() {
        thread.start()
    }
}

class UpdateCityWeatherTask(
    private val contentResolver: ContentResolver,
    private val rowId: String,
    private val values: ContentValues
) {
    private var onUpdateCityWeatherListener: ((String) -> Unit)? = null
    fun setOnUpdateCityWeatherListener(listener: (String) -> Unit) {
        onUpdateCityWeatherListener = listener
    }

    private val thread: Thread

    init {
        val handler = Handler()
        thread = Thread {
            val row = contentResolver.update(
                CityWeatherEntry.CONTENT_URI,
                values,
                CityWeatherEntry.ID,
                arrayOf(rowId)
            )

            handler.post {
                onUpdateCityWeatherListener?.invoke(row.toString())
            }
        }
    }

    fun start() {
        thread.start()
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