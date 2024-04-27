package com.kerolosatya.dailyforecast.domain.repository

import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel

interface CitiesRepository {
    suspend fun getCities(): ResponseCityModel
}
