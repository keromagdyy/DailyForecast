package com.kerolosatya.dailyforecast.data.dataSource.cities

import com.kerolosatya.dailyforecast.data.api.ICitiesApis
import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import retrofit2.Response
import com.kerolosatya.dailyforecast.di.NetModule.CitiesRetrofit

class CitiesRemoteDataSourceImpl(
    @CitiesRetrofit private val iApis: ICitiesApis
) : CitiesRemoteDataSource {
    override suspend fun getCities(): Response<ResponseCityModel> {
        return iApis.getCities()
    }
}

