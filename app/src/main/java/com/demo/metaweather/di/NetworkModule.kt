package com.demo.metaweather.di

import com.demo.metaweather.data.datasources.remote.impl.network.MetaWeatherNetworkApi
import com.demo.metaweather.data.datasources.remote.impl.network.MetaWeatherRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideWeatherRetrofitApi(): MetaWeatherNetworkApi {
        return MetaWeatherRetrofit.getMetaWeatherApi()
    }
}
