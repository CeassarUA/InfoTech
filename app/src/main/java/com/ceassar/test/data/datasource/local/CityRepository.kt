package com.ceassar.test.data.datasource.local

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ceassar.test.data.datasource.local.database.AppDatabase
import com.ceassar.test.data.datasource.local.database.CityEntity
import kotlinx.coroutines.flow.Flow


class CityRepository(
    database: AppDatabase
) {
    private val dao = database.cityDao()

    fun getSearchResultStream(query: String): Flow<PagingData<CityEntity>> {
        Log.d("Repository", "New query: $query")
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { dao.getPaging("%$query%") }
        ).flow
    }

    fun getSearchResultStream(): Flow<PagingData<CityEntity>> {
        Log.d("Repository", "get All")
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { dao.getDatasource() }
        ).flow
    }


}