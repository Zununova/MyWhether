package com.example.mywhether.data.repositories

import android.util.Log
import com.example.mywhether.data.local.daos.CityDao
import com.example.mywhether.data.local.entities.CityEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CityRepository @Inject constructor(private val cityDao: CityDao) {

    suspend fun setCity(city: String) {
        cityDao.setCity(CityEntity(city))
    }

    fun getCityList() = flow {
        try {
            emit(cityDao.getCityList())
        }catch (exception: Exception) {
            Log.e("cityError", "getCityList: ${exception.message}")
        }
    }

    suspend fun deleteCity(city: String) {
        cityDao.deleteCity(city)
    }
}