package com.kerolosatya.dailyforecast.data.model.weather

data class ForecastModel(
    val city: String,
    val dt: Long,
    val main: MainModel,
    val weather: List<WeatherModel>,
)
