package com.yatochk.weather.model.database

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log

private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${CityWeatherEntry.TABLE_NAME} (" +
            "${CityWeatherEntry.ID} INTEGER PRIMARY KEY," +
            "${CityWeatherEntry.CITY} TEXT," +
            "${CityWeatherEntry.TEMPERATURE} TEXT," +
            "${CityWeatherEntry.FILE_NAME} TEXT)"

private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CityWeatherEntry.TABLE_NAME}"

private const val CITIES = 100
private const val CITY_ID = 101

class CitiesWeatherProvider : ContentProvider() {

    private lateinit var dbHelper: WeatherDbHelper

    companion object {
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(CONTENT_AUTHORITY, PATH_CITIES_WEATHER, CITIES)
            uriMatcher.addURI(CONTENT_AUTHORITY, "$PATH_CITIES_WEATHER/#", CITY_ID)
        }
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {
        val match = uriMatcher.match(uri)
        when (match) {
            CITIES -> return insertCityWeather(uri!!, values!!)!!
            else -> throw IllegalArgumentException("Insertion is not supported for $uri")
        }
    }

    override fun query(
        uri: Uri?,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val database = dbHelper.readableDatabase
        val cursor: Cursor

        val match = uriMatcher.match(uri)
        when (match) {
            CITIES ->
                cursor = database.query(
                    CityWeatherEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
                )
            CITY_ID -> {
                val selectionId = CityWeatherEntry.ID + "=?"
                val selectionArgsId = arrayOf(ContentUris.parseId(uri).toString())

                cursor = database.query(
                    CityWeatherEntry.TABLE_NAME,
                    projection,
                    selectionId,
                    selectionArgsId,
                    null,
                    null,
                    sortOrder
                )
            }
            else -> throw IllegalArgumentException("Cannot query unknown URI $uri")
        }
        return cursor
    }

    override fun onCreate(): Boolean {
        dbHelper = WeatherDbHelper(context!!)
        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val match = uriMatcher.match(uri)
        return when (match) {
            CITIES -> updateCityWeather(values!!, selection!!, selectionArgs!!)
            CITY_ID -> {
                val updateSelection = CityWeatherEntry.ID + "=?"
                val updateSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                updateCityWeather(values!!, updateSelection, updateSelectionArgs)
            }
            else -> throw IllegalArgumentException("Update is not supported for $uri")
        }
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        val database = dbHelper.writableDatabase
        val match = uriMatcher.match(uri)
        return when (match) {
            CITIES -> {
                val deleteSelection = "$selection=?"
                database.delete(CityWeatherEntry.TABLE_NAME, deleteSelection, selectionArgs)
            }
            CITY_ID -> {
                val deleteSelection = CityWeatherEntry.ID + "=?"
                val deleteSelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                database.delete(CityWeatherEntry.TABLE_NAME, deleteSelection, deleteSelectionArgs)
            }
            else -> throw IllegalArgumentException("Deletion is not supported for $uri")
        }
    }

    override fun getType(uri: Uri?): String {
        val match = uriMatcher.match(uri)
        return when (match) {
            CITIES -> CityWeatherEntry.CONTENT_LIST_TYPE
            CITY_ID -> CityWeatherEntry.CONTENT_ITEM_TYPE
            else -> throw IllegalStateException("Unknown URI $uri with match $match")
        }
    }

    private fun insertCityWeather(uri: Uri, values: ContentValues): Uri? {
        values.getAsString(CityWeatherEntry.CITY) ?: throw IllegalArgumentException("City name??")

        val database = dbHelper.writableDatabase
        val id = database.insert(CityWeatherEntry.TABLE_NAME, null, values)
        if (id.toInt() == -1) {
            Log.e("Provider", "Failed to insert row for $uri")
            return null
        }

        return ContentUris.withAppendedId(uri, id)
    }

    private fun updateCityWeather(
        values: ContentValues,
        selection: String,
        selectionArgs: Array<out String>
    ): Int {
        if (values.containsKey(CityWeatherEntry.CITY))
            values.getAsString(CityWeatherEntry.CITY) ?: throw IllegalArgumentException("City requires a name")

        if (values.size() == 0) {
            return 0
        }

        val database = dbHelper.writableDatabase
        return database.update(CityWeatherEntry.TABLE_NAME, values, "$selection=?", selectionArgs)
    }
}

class WeatherDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "citiesWeather.db"
    }
}
