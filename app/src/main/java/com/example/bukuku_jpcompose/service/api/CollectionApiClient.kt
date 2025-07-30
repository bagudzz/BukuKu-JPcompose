package com.example.bukuku_jpcompose.service.api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CollectionApiClient {
    private const val BASE_URL = "http://192.168.1.38:3000/"

    val instance: CollectionApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CollectionApiService::class.java)
    }
}
