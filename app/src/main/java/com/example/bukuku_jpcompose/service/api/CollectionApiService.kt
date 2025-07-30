package com.example.bukuku_jpcompose.service.api

import com.example.bukuku_jpcompose.model.response.BookItem
import retrofit2.http.*

interface CollectionApiService {
    @GET("collection")
    suspend fun getCollection(
        @Header("Authorization") token: String
    ): List<BookItem>

    @POST("collection")
    suspend fun addBook(
        @Header("Authorization") token: String,
        @Body book: BookItem
    )

    @HTTP(method = "DELETE", path = "collection", hasBody = true)
    suspend fun deleteBook(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    )
}
