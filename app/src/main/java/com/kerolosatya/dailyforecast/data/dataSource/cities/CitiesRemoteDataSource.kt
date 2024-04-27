package com.kerolosatya.dailyforecast.data.dataSource.cities

import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import retrofit2.Response

interface CitiesRemoteDataSource {
    suspend fun getCities(): Response<ResponseCityModel>
}