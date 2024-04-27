package com.kerolosatya.dailyforecast.data.api

import com.kerolosatya.dailyforecast.data.util.ConstantLinks
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherApis {

    @GET(ConstantLinks.FORECAST)
    suspend fun getWeather(
        @Query("lat") lat : String,
        @Query("lon") lon : String,
    ): Response<ResponseForecastModel>

}