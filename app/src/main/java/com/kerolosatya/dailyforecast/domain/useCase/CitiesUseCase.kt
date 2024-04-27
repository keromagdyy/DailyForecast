package com.kerolosatya.dailyforecast.domain.useCase

import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.domain.repository.CitiesRepository

class CitiesUseCase(private val citiesRepository: CitiesRepository) {
    suspend fun execute(): ResponseCityModel {
        return citiesRepository.getCities()
    }
}