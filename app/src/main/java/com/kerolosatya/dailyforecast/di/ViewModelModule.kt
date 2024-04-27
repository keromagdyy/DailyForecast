package com.kerolosatya.dailyforecast.di

import android.app.Application
import com.kerolosatya.dailyforecast.domain.useCase.CitiesUseCase
import com.kerolosatya.dailyforecast.domain.useCase.ForecastUseCase
import com.kerolosatya.dailyforecast.ui.home.CitiesViewModel
import com.kerolosatya.dailyforecast.ui.home.ForecastViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    fun provideCompetitionDetailsViewModel(app: Application, forecastUseCase: ForecastUseCase): ForecastViewModel {
        return ForecastViewModel(app,forecastUseCase)
    }

    @Provides
    fun provideCitiesViewModel(app: Application, citiesUseCase: CitiesUseCase): CitiesViewModel {
        return CitiesViewModel(app,citiesUseCase)
    }
}