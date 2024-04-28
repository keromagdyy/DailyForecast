package com.kerolosatya.dailyforecast.di

import com.kerolosatya.dailyforecast.data.api.ICitiesApis
import com.kerolosatya.dailyforecast.data.api.IWeatherApis
import com.kerolosatya.dailyforecast.data.util.ConstantLinks
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class WeatherRetrofit

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class CitiesRetrofit


    @Singleton
    @Provides
    @WeatherRetrofit
    fun provideWeatherRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ConstantLinks.BASE_URL_WEATHER)
            .client(getOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherAPIService(@WeatherRetrofit retrofit: Retrofit): IWeatherApis {
        return retrofit.create(IWeatherApis::class.java)
    }

    @Singleton
    @Provides
    @CitiesRetrofit
    fun provideCitiesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ConstantLinks.BASE_URL_CITIES)
            .client(getOkHttpClient())
            .build()
    }

    @Singleton
    @Provides
    fun provideCitiesAPIService(@CitiesRetrofit retrofit: Retrofit): ICitiesApis {
        return retrofit.create(ICitiesApis::class.java)
    }

    private fun getOkHttpClient() = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("appid", ConstantLinks.API_KEY)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .build()
}
