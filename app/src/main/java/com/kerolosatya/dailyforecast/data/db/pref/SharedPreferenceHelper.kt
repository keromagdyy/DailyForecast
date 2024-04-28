package com.kerolosatya.dailyforecast.data.db.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kerolosatya.dailyforecast.data.model.cities.CityModel
import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.data.model.weather.CachedForecastModel
import com.kerolosatya.dailyforecast.data.model.weather.ForecastModel
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel

object SharedPreferenceHelper {

    private const val mySharedPreferenceName = "DailyForecast"
    private const val mySharedPreferenceCitiesObj = "cities"
    private const val mySharedPreferenceForecastObj = "forecast"
    private var mAppContext: Context? = null

    fun init(appContext: Context?) {
        mAppContext = appContext
    }

    private val sharedPreferences: SharedPreferences
        get() = mAppContext!!.getSharedPreferences(
            mySharedPreferenceName,
            Context.MODE_PRIVATE
        )

    var citiesList: List<CityModel>? // Assuming CityModel is inside ResponseCityModel
        get() {
            val sharedPref = sharedPreferences
            val gson = Gson()
            val json = sharedPref.getString(mySharedPreferenceCitiesObj, "[]") // Empty list as default

            val type = object : TypeToken<List<CityModel>>() {}.type
            return gson.fromJson(json, type)
        }
        set(value) {
            val sharedPref = sharedPreferences.edit()
            val gson = Gson()
            val json = gson.toJson(value) // Access the actual list of cities from ResponseCityModel
            sharedPref.putString(mySharedPreferenceCitiesObj, json).apply()
        }

    var forecastObj: CachedForecastModel?
        get() {
            val gson = Gson()
            val user = sharedPreferences.getString(mySharedPreferenceForecastObj, "")
            return gson.fromJson(user, CachedForecastModel::class.java)
        }
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            val editor = sharedPreferences.edit()
            editor.putString(mySharedPreferenceForecastObj, json).apply()

        }


}