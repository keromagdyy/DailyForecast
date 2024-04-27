package com.kerolosatya.dailyforecast.di

import com.kerolosatya.dailyforecast.data.dataSource.cities.CitiesRemoteDataSource
import com.kerolosatya.dailyforecast.data.dataSource.cities.CitiesRepositoryImpl
import com.kerolosatya.dailyforecast.data.dataSource.weather.ForecastRemoteDataSource
import com.kerolosatya.dailyforecast.data.dataSource.weather.ForecastRepositoryImpl
import com.kerolosatya.dailyforecast.domain.repository.CitiesRepository
import com.kerolosatya.dailyforecast.domain.repository.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideForecastRepository(
        forecastRemoteDataSource: ForecastRemoteDataSource,
    ): ForecastRepository {

        return ForecastRepositoryImpl(
            forecastRemoteDataSource
        )
    }

    @Provides
    @Singleton
    fun provideCitiesRepository(
        citiesRemoteDataSource: CitiesRemoteDataSource,
    ): CitiesRepository {

        return CitiesRepositoryImpl(
            citiesRemoteDataSource
        )
    }
}