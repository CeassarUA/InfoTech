package com.ceassar.test.di

import android.app.Application
import android.util.Log
import com.ceassar.test.data.datasource.remote.MainAPI
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CACHE_SIZE = 10_000_000L
private const val TIMEOUT = 60L
private const val SERVER_CLIENT = "serverClient"
private const val SERVER_API = "serverApi"

val network: Module = module {
    single { GsonBuilder().setLenient().create() }

    factory { provideHttpCache(get()) }
    factory { provideOkhttpClientBuilder(get()) }
    factory { provideRetrofitBuilder(get()) }

    factory(named(SERVER_CLIENT)) { provideOkhttpClient(get()) }

    single(named(SERVER_API)) { provideServerRetrofit(get(), get(named(SERVER_CLIENT))) }

    single { provideApi(get(named(SERVER_API))) }

}


fun provideHttpCache(application: Application): Cache {
    val cacheSize = CACHE_SIZE
    return Cache(application.cacheDir, cacheSize)
}

private fun provideApi(retrofit: Retrofit): MainAPI {
    return retrofit.create(MainAPI::class.java)
}


private fun provideOkhttpClientBuilder(cache: Cache): OkHttpClient.Builder {
    return OkHttpClient().newBuilder().apply {
        cache(cache)
            val httpLoggingInterceptor = HttpLoggingInterceptor { message ->
                if (!message.contains("�")) {
                    Log.d("OkHttp", message)
                }
            }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
            addInterceptor(httpLoggingInterceptor)
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    }
}

private fun provideOkhttpClient(builder: OkHttpClient.Builder): OkHttpClient {
    return builder.build()
}


private fun provideRetrofitBuilder(gson: Gson): Retrofit.Builder {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
}

private fun provideServerRetrofit(builder: Retrofit.Builder, okHttpClient: OkHttpClient): Retrofit {
    return builder.client(okHttpClient)
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .build()
}
