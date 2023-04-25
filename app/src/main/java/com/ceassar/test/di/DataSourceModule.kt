package com.ceassar.test.di

import com.ceassar.test.data.datasource.local.AssetsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val dataSource: Module = module {
    single {
        AssetsRepository(androidContext())
    }
}