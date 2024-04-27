package com.kerolosatya.dailyforecast.di

import com.kerolosatya.dailyforecast.data.api.ICitiesApis
import com.kerolosatya.dailyforecast.data.api.IWeatherApis
import com.kerolosatya.dailyforecast.data.dataSource.cities.CitiesRemoteDataSource
import com.kerolosatya.dailyforecast.data.dataSource.cities.CitiesRemoteDataSourceImpl
import com.kerolosatya.dailyforecast.data.dataSource.weather.ForecastRemoteDataSource
import com.kerolosatya.dailyforecast.data.dataSource.weather.ForecastRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideCompetitionRemoteDataSource(
        aPIService: IWeatherApis
    ): ForecastRemoteDataSource {
        return ForecastRemoteDataSourceImpl(aPIService)
    }

    @Singleton
    @Provides
    fun provideCitiesRemoteDataSource(
        aPIService: ICitiesApis
    ): CitiesRemoteDataSource {
        return CitiesRemoteDataSourceImpl(aPIService)
    }
}