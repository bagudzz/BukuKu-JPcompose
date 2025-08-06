package com.example.bukuku_jpcompose.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton Retrofit client untuk Collection API.
 */
object CollectionApiClient {
    private const val BASE_URL = "http://192.168.1.7:3000/" // ✅URL API Next.js

    val instance: CollectionApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CollectionApiService::class.java)
    }
}
