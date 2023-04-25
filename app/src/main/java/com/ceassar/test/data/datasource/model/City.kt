package com.ceassar.test.data.datasource.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val id: Int,
    val name: String
) : Parcelable

