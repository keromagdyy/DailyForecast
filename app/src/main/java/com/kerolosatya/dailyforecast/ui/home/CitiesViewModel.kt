package com.kerolosatya.dailyforecast.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.domain.useCase.CitiesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class CitiesApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val app: Application,
    private val citiesUseCase: CitiesUseCase,
) : ViewModel() {

    private val _statusCities = MutableLiveData<CitiesApiStatus>()
    val statusCities: LiveData<CitiesApiStatus>
        get() = _statusCities

    private var _cities = MutableLiveData<ResponseCityModel>()
    val cities: LiveData<ResponseCityModel>
        get() = _cities

    init {
        getCities()
    }

    private fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _statusCities.postValue(CitiesApiStatus.LOADING)
                val citiesData = citiesUseCase.execute()
                _statusCities.postValue(CitiesApiStatus.DONE)
                _cities.postValue(citiesData)
                Log.d("getCitiesException", "getCities: ${citiesData}")
            } catch (e: Exception) {
                _statusCities.postValue(CitiesApiStatus.ERROR)
                Log.d("getCitiesException", "getCities: ${e.message}")
            }
        }
    }

}