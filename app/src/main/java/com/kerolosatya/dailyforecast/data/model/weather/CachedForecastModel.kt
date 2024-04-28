package com.kerolosatya.dailyforecast.data.model.weather

import com.kerolosatya.dailyforecast.data.model.cities.CityModel

data class CachedForecastModel(
    var city: CityModel = CityModel(),
    val list: List<ForecastModel> = listOf(),
)