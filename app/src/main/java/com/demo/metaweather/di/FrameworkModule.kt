package com.demo.metaweather.di

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import com.demo.metaweather.data.datasources.remote.impl.RemoteWeatherDatasourceImpl
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

@Module
@InstallIn(SingletonComponent::class)
abstract class FrameworkModule {
    @Binds
    abstract fun bindRemoteWeatherDataSource(remoteWeatherDatasource: RemoteWeatherDatasourceImpl): RemoteWeatherDatasource

    @Binds
    abstract fun bindWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindDateFormatter(weatherDatesFormatter: WeatherDatesFormatter): DateFormatter

    @Binds
    abstract fun bindImageLoader(imageLoader: CoilImageLoader): ImageLoader
}
