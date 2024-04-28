package com.kerolosatya.dailyforecast.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kerolosatya.dailyforecast.data.model.weather.ResponseForecastModel
import com.kerolosatya.dailyforecast.data.util.Resource
import com.kerolosatya.dailyforecast.domain.useCase.ForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val forecastUseCase: ForecastUseCase,
) : ViewModel() {


    private val forecastMutableLiveData: MutableLiveData<Resource<ResponseForecastModel>> = MutableLiveData()

    fun getForecast(lat: String, lon: String): LiveData<Resource<ResponseForecastModel>> {
        forecastMutableLiveData.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val forecastData = forecastUseCase.execute(lat, lon)
                forecastMutableLiveData.postValue(Resource.Success(forecastData))
                Log.d("getForecastException", "getForecast: ${forecastMutableLiveData}")

            } catch (e: Exception) {
                forecastMutableLiveData.postValue(Resource.Error(e.message.toString()))
                Log.d("getForecastException", "getForecast: ${e.message}")
            }

        }

        return forecastMutableLiveData
    }

}