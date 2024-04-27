package com.kerolosatya.dailyforecast.data.dataSource.weather

import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.domain.repository.ForecastRepository

class ForecastRepositoryImpl(
    private val forecastRemoteDataSource: ForecastRemoteDataSource,
) : ForecastRepository {

    override suspend fun getForecast(lat: String, lon: String): ResponseForecastModel {
        val forecastModel = ResponseForecastModel()

        if (forecastRemoteDataSource.getForecast(lat, lon).isSuccessful) {
            val body = forecastRemoteDataSource.getForecast(lat, lon).body()
            if (body != null) {
                return body
            }
        }
        return forecastModel
    }
}