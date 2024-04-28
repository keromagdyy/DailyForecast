package com.kerolosatya.dailyforecast.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.data.util.Resource
import com.kerolosatya.dailyforecast.domain.useCase.CitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val citiesUseCase: CitiesUseCase,
) : ViewModel() {

    private val citiesMutableLiveData: MutableLiveData<Resource<ResponseCityModel>> = MutableLiveData()

    fun getCities(): LiveData<Resource<ResponseCityModel>> {
        citiesMutableLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val forecastData = citiesUseCase.execute()
                citiesMutableLiveData.postValue(Resource.Success(forecastData))
                Log.d("getForecastException", "getForecast: ${citiesMutableLiveData}")

            } catch (e: Exception) {
                citiesMutableLiveData.postValue(Resource.Error(e.message.toString()))
                Log.d("getForecastException", "getForecast: ${e.message}")
            }

        }

        return citiesMutableLiveData
    }

}