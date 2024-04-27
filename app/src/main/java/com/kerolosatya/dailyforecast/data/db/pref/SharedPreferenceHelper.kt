package com.kerolosatya.dailyforecast.data.db.pref

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
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

    var citiesObj: ResponseCityModel?
        get() {
            val gson = Gson()
            val user = sharedPreferences.getString(mySharedPreferenceCitiesObj, "")
            return gson.fromJson(user, ResponseCityModel::class.java)
        }
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            val editor = sharedPreferences.edit()
            editor.putString(mySharedPreferenceCitiesObj, json).apply()

        }

    var forecastObj: ForecastModel?
        get() {
            val gson = Gson()
            val user = sharedPreferences.getString(mySharedPreferenceForecastObj, "")
            return gson.fromJson(user, ForecastModel::class.java)
        }
        set(value) {
            val gson = Gson()
            val json = gson.toJson(value)
            val editor = sharedPreferences.edit()
            editor.putString(mySharedPreferenceForecastObj, json).apply()

        }

}