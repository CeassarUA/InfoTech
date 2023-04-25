package com.ceassar.test.data.datasource.local

import android.content.Context
import com.ceassar.test.R
import com.ceassar.test.data.datasource.model.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class AssetsRepository(
    val context: Context
) {
    private fun Context.getJson(): String =
        resources.openRawResource(R.raw.city_list).bufferedReader().use { it.readText() }

    fun getList(): List<City> {
        val listType = object : TypeToken<ArrayList<City>>() {}.type
        return Gson().fromJson(context.getJson(), listType)
    }

}