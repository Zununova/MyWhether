package com.example.mywhether.data.local.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mywhether.data.local.entities.CityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setCity(city: CityEntity)

    @Query("SELECT * FROM CityEntity")
    fun getCityList() : Flow<List<CityEntity>>

    @Query("DELETE FROM CityEntity WHERE city LIKE '%' || :city || '%'")
    suspend fun deleteCity(city: String)
}