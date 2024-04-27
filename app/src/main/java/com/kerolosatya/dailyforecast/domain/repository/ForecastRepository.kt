package com.kerolosatya.dailyforecast.domain.repository

import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel

interface ForecastRepository {
    suspend fun getForecast(lat: String, lon: String): ResponseForecastModel
}
