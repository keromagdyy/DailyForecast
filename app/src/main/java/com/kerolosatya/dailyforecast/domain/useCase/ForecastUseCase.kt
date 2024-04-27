package com.kerolosatya.dailyforecast.domain.useCase

import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.domain.repository.ForecastRepository

class ForecastUseCase(private val forecastRepository: ForecastRepository) {
    suspend fun execute(lat: String, lon: String): ResponseForecastModel {
        return forecastRepository.getForecast(lat,lon)
    }
}