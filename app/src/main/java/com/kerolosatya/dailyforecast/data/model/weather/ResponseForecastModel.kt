package com.kerolosatya.dailyforecast.data.model.weather

data class ResponseForecastModel(
    val cod: String = "",
    val message: Int = 0,
    val cnt: Int = 0,
    val list: List<ForecastModel> = listOf(),
)