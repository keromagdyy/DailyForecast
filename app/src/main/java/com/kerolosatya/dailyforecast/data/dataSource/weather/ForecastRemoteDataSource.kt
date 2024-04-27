package com.kerolosatya.dailyforecast.data.dataSource.weather

import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import retrofit2.Response

interface ForecastRemoteDataSource {
    suspend fun getForecast(lat: String, lon: String): Response<ResponseForecastModel>
}