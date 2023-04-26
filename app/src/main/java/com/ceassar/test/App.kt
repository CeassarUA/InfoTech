package com.ceassar.test

import android.app.Application
import com.ceassar.test.di.dataSource
import com.ceassar.test.di.network
import com.ceassar.test.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    viewModel,
                    dataSource,
                    network
                )
            )
        }
    }
}