package com.example.mywhether.data.remote

import com.example.mywhether.data.remote.apiservices.ForecastWhetherApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WhetherRetrofitClient {

    val retrofitClient = Retrofit.Builder()
        .client(
            OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        )
        .baseUrl("https://api.weatherapi.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun provideCurrentWhetherApiService() =
        retrofitClient.create(ForecastWhetherApiService::class.java)
}