package com.example.mywhether.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mywhether.data.local.daos.CityDao
import com.example.mywhether.data.local.entities.CityEntity

@Database(entities = [CityEntity::class], version = 1)
abstract class CityDataBase : RoomDatabase() {
    abstract val cityDao: CityDao
}