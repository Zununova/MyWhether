package com.example.mywhether.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityEntity(
    val city: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
