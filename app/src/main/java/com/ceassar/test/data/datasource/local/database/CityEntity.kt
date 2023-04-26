package com.ceassar.test.data.datasource.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    val id: Int = 0,
    val coord: Coord = Coord(),
    val country: String = "",
    val name: String = "",
    val state: String = ""
) {
    data class Coord(
        val lat: Double = 0.0,
        val lon: Double = 0.0
    )
}

