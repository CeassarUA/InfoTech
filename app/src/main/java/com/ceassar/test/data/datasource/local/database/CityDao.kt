package com.ceassar.test.data.datasource.local.database

import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ceassar.test.data.datasource.model.CityEntity

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(source: CityEntity)

    @Query("SELECT * FROM city WHERE name like :text")
    fun getPaging(text: String): PagingSource<Int, CityEntity>

    @Query("SELECT * FROM city")
    fun getDatasource(): PagingSource<Int, CityEntity>


}