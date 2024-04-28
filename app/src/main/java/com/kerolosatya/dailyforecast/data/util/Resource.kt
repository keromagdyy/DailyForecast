package com.kerolosatya.dailyforecast.data.util

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Loading<T>() : Resource<T>()
    class Error<T>(val error: String?) : Resource<T>()
}