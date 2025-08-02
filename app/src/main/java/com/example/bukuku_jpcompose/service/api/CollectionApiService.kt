package com.example.bukuku_jpcompose.service.api

import com.example.bukuku_jpcompose.model.request.BookItemRequest
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.response.BooksResponseWithMessage
import com.example.bukuku_jpcompose.model.response.CollectionResponse
import retrofit2.Response
import retrofit2.http.*

data class DeleteRequest(
    val id_book: String
)

interface CollectionApiService {

    // ✅ Ambil semua koleksi user
    @GET("api/collection")
    suspend fun getUserCollection(
        @Header("Authorization") token: String
    ): Response<CollectionResponse>

    // ✅ Tambah buku ke koleksi user
    @POST("api/collection")
    suspend fun addBookToCollection(
        @Header("Authorization") token: String,
        @Body book: BookItemRequest
    ): Response<BooksResponseWithMessage>

    // ✅ Hapus buku dari koleksi user
    @HTTP(method = "DELETE", path = "api/collection", hasBody = true)
    suspend fun deleteBookFromCollection(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>
    ): Response<Unit>
}
