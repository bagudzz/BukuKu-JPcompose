package com.example.bukuku_jpcompose.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BooksApiClient {
    private const val BASE_URL = "https://www.googleapis.com/books/v1/"

    val instance: BooksApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BooksApiService::class.java)
    }
}