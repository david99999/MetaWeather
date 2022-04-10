package com.demo.metaweather.di

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import com.demo.metaweather.data.datasources.remote.impl.RemoteWeatherDatasourceImpl
import com.demo.metaweather.data.repositories.WeatherRepository
import com.demo.metaweather.data.repositories.WeatherRepositoryImpl
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

//    @Provides
//    @Singleton
//    fun providePhotosRepository(@ApplicationContext context: Context): PhotosRepository {
//        return PhotosRepositoryImpl(context)
}
