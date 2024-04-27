package com.kerolosatya.dailyforecast.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.dailyforecast.data.model.cities.ResponseCityModel
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.domain.useCase.CitiesUseCase
import com.kerolosatya.dailyforecast.domain.useCase.ForecastUseCase
import com.kerolosatya.dailyforecast.ui.base.BaseApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val app: Application,
    private val citiesUseCase: CitiesUseCase,
) : ViewModel() {

    private val _statusCities = MutableLiveData<BaseApiStatus>()
    val statusCities: LiveData<BaseApiStatus>
        get() = _statusCities

    private var _cities = MutableLiveData<ResponseCityModel>()
    val cities: LiveData<ResponseCityModel>
        get() = _cities


    private fun getCities() {
        viewModelScope.launch(Dispatchers.IO) {
            _statusCities.postValue(BaseApiStatus.LOADING)
            try {
                _statusCities.postValue(BaseApiStatus.DONE)

                _cities.postValue(citiesUseCase.execute())
                Log.d("getCitiesException", "getCities: ${cities}")

            } catch (e: Exception) {
                _statusCities.postValue(BaseApiStatus.ERROR)
                Log.d("getCitiesException", "getCities: ${e.message}")
            }

        }
    }

}