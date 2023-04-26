package com.ceassar.test.data.datasource.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey
    val id: Int,
    val coord: Coord = Coord(),
    val country: String = "",
    val name: String = "",
    val state: String = ""
) : Parcelable {
    @Parcelize

    data class Coord(
        val lat: Double = 0.0,
        val lon: Double = 0.0
    ) : Parcelable
}

