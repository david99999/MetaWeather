package com.demo.metaweather.di

import com.demo.metaweather.data.repositories.WeatherRepository
import com.demo.metaweather.data.repositories.WeatherRepositoryImpl
import com.demo.metaweather.utils.CoilImageLoader
import com.demo.metaweather.utils.DateFormatter
import com.demo.metaweather.utils.ImageLoader
import com.demo.metaweather.utils.WeatherDatesFormatter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * D.I. module for providing any other framework-dependant classes
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class FrameworkModule {

    @Binds
    abstract fun bindWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindDateFormatter(weatherDatesFormatter: WeatherDatesFormatter): DateFormatter

    @Binds
    abstract fun bindImageLoader(imageLoader: CoilImageLoader): ImageLoader
}
