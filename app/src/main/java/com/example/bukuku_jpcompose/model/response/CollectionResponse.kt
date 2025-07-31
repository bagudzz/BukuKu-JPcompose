package com.example.bukuku_jpcompose.model.response

data class CollectionResponse(
    val code: Int,
    val message: String,
    val data: List<CollectionBook>
)

data class CollectionBook(
    val id: String,
    val id_book: String,
    val title: String,
    val description: String,
    val thumbnail: String
)