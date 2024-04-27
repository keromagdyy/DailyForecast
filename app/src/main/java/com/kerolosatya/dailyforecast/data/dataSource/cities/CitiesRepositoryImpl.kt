package com.kerolosatya.dailyforecast.data.dataSource.cities

import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.domain.repository.CitiesRepository
import com.kerolosatya.dailyforecast.domain.repository.ForecastRepository

class CitiesRepositoryImpl(
    private val forecastRemoteDataSource: CitiesRemoteDataSource,
) : CitiesRepository {

    override suspend fun getCities(): ResponseCityModel {
        val cityModel = ResponseCityModel()

        if (forecastRemoteDataSource.getCities().isSuccessful) {
            val body = forecastRemoteDataSource.getCities().body()
            if (body != null) {
                return body
            }
        }
        return cityModel
    }
}