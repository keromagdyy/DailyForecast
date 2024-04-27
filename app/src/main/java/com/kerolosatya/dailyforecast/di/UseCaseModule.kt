package com.kerolosatya.dailyforecast.di

import com.kerolosatya.dailyforecast.domain.repository.CitiesRepository
import com.kerolosatya.dailyforecast.domain.repository.ForecastRepository
import com.kerolosatya.dailyforecast.domain.useCase.CitiesUseCase
import com.kerolosatya.dailyforecast.domain.useCase.ForecastUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    fun provideGetForecastUseCase(forecastRepository: ForecastRepository): ForecastUseCase {
        return ForecastUseCase(forecastRepository)
    }

    @Provides
    fun provideGetCitiesUseCase(citiesRepository: CitiesRepository): CitiesUseCase {
        return CitiesUseCase(citiesRepository)
    }

}