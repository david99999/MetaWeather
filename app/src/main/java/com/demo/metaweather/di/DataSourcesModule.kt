package com.demo.metaweather.di

import com.demo.metaweather.data.datasources.remote.RemoteWeatherDatasource
import com.demo.metaweather.data.datasources.remote.impl.network.MetaWeatherRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * D.I. module for providing the implementations for the defined datasources,
 * currently only exists [RemoteWeatherDatasource] but if any local datasource
 * would be defined, here should exists the provider for its implementation
 */
@Module
@InstallIn(SingletonComponent::class)
class DataSourcesModule {

    @Provides
    @Singleton
    fun provideRemoteWeatherDatasource(): RemoteWeatherDatasource {
        return MetaWeatherRetrofit.getMetaWeatherApi()
    }
}
