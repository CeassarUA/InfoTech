package com.ceassar.test.di

import com.ceassar.test.data.datasource.local.CityRepository
import com.ceassar.test.data.datasource.local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val dataSource: Module = module {
    single {
        CityRepository(get())
    }

    single {
        AppDatabase.getInstance(androidContext())
    }
}