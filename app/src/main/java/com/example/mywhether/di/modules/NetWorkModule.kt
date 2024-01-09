package com.example.mywhether.di.modules

import com.example.mywhether.data.remote.WhetherRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetWorkModule {

    @Singleton
    val retrofitClient = WhetherRetrofitClient()

    @Singleton
    @Provides
    fun provideCurrentWhetherApiService() = retrofitClient.provideCurrentWhetherApiService()

}