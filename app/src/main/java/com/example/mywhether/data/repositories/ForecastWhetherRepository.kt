package com.example.mywhether.data.repositories

import android.util.Log
import com.example.mywhether.data.Either
import com.example.mywhether.data.remote.apiservices.ForecastWhetherApiService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ForecastWhetherRepository @Inject constructor(private val currentWhetherRepository: ForecastWhetherApiService) {

    fun getCurrentWhether(city: String) = flow {
        try {
            val request = currentWhetherRepository.getForecastWhether(q = city)
            if (request.isSuccessful) {
                request.body()?.let {
                    emit(Either.onSuccess(it))
                }
            }else{
                emit(Either.onError(request.code()))
            }
        } catch (exception: Exception) {
            Log.e("TAG", "getCurrentWhether: ${exception.message}", )
            emit(Either.onError(1))
        }
    }
}