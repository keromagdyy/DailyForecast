package com.kerolosatya.dailyforecast.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.domain.useCase.ForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ForecastApiStatus { LOADING, ERROR, DONE }

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val app: Application,
    private val forecastUseCase: ForecastUseCase,
) : ViewModel() {

    private val _statusForecast = MutableLiveData<ForecastApiStatus>()
    val statusForecast: LiveData<ForecastApiStatus>
        get() = _statusForecast

    private var _forecast = MutableLiveData<ResponseForecastModel>()
    val forecast: LiveData<ResponseForecastModel>
        get() = _forecast


    fun getForecast(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _statusForecast.postValue(ForecastApiStatus.LOADING)
            try {
                val forecastData = forecastUseCase.execute(lat, lon)
                _statusForecast.postValue(ForecastApiStatus.DONE)
                _forecast.postValue(forecastData)
                Log.d("getForecastException", "getForecast: ${forecast}")

            } catch (e: Exception) {
                _statusForecast.postValue(ForecastApiStatus.ERROR)
                Log.d("getForecastException", "getForecast: ${e.message}")
            }

        }
    }

}