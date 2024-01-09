package com.example.mywhether.di.modules

import android.content.Context
import androidx.room.Room
import com.example.mywhether.data.local.CityDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun cityDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        CityDataBase::class.java,
        "CityDataBase"
    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideCityDao(cityDataBase: CityDataBase) = cityDataBase.cityDao
}