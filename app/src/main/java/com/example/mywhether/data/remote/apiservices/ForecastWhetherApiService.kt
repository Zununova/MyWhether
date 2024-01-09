package com.example.mywhether.data.remote.apiservices

import com.example.mywhether.data.remote.models.WhetherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastWhetherApiService {

    @GET("forecast.json")
    suspend fun getForecastWhether(
        @Query("key") key: String = "56140e7efb8b4d55a19130823232812",
        @Query ("q") q: String
    ) : Response<WhetherModel>
}