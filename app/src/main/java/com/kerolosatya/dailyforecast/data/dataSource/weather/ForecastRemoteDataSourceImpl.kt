package com.kerolosatya.dailyforecast.data.dataSource.weather

import com.kerolosatya.dailyforecast.data.api.IWeatherApis
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import retrofit2.Response
import com.kerolosatya.dailyforecast.di.NetModule.WeatherRetrofit

class ForecastRemoteDataSourceImpl(
    @WeatherRetrofit private val iApis: IWeatherApis
) : ForecastRemoteDataSource {
    override suspend fun getForecast(lat: String, lon: String): Response<ResponseForecastModel> {
        return iApis.getWeather(lat, lon)
    }
}

