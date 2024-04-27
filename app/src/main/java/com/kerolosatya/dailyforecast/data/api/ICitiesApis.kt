package com.kerolosatya.dailyforecast.data.api

import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.data.util.ConstantLinks
import retrofit2.Response
import retrofit2.http.GET

interface ICitiesApis {

    @GET(ConstantLinks.CITIES)
    suspend fun getCities(): Response<ResponseCityModel>

}