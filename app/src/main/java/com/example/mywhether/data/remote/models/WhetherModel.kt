package com.example.mywhether.data.remote.models


import com.google.gson.annotations.SerializedName

data class WhetherModel(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)