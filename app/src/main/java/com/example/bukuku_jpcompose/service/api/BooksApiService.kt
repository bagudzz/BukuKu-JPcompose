package com.example.bukuku_jpcompose.service.api

import com.example.bukuku_jpcompose.model.response.BooksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {

    // Contoh: https://www.googleapis.com/books/v1/volumes?q=android
    @GET("volumes")
    suspend fun getBooks(@Query("q") query: String = "buku" ): BooksResponse// Default pencarian
}