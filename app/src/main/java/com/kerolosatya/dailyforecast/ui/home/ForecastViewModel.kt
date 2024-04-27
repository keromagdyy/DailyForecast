package com.kerolosatya.dailyforecast.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.domain.useCase.ForecastUseCase
import com.kerolosatya.dailyforecast.ui.base.BaseApiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val app: Application,
    private val forecastUseCase: ForecastUseCase,
) : ViewModel() {

    private val _statusForecast = MutableLiveData<BaseApiStatus>()
    val statusForecast: LiveData<BaseApiStatus>
        get() = _statusForecast

    private var _forecast = MutableLiveData<ResponseForecastModel>()
    val forecast: LiveData<ResponseForecastModel>
        get() = _forecast


    private fun getForecast(lat: String, lon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _statusForecast.postValue(BaseApiStatus.LOADING)
            try {
                _statusForecast.postValue(BaseApiStatus.DONE)

                _forecast.postValue(forecastUseCase.execute(lat, lon))
                Log.d("getForecastException", "getForecast: ${forecast}")

            } catch (e: Exception) {
                _statusForecast.postValue(BaseApiStatus.ERROR)
                Log.d("getForecastException", "getForecast: ${e.message}")
            }

        }
    }

}