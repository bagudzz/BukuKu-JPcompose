package com.example.bukuku_jpcompose.model.response

data class BooksResponse(
    val items: List<BookItem>? = null
)

data class BooksResponseWithMessage(
    val code: Int,
    val message : String,
    val data : BookItem
)

data class BookItem(

    val id: String? = null,
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String,
    val description: String,
    val imageLinks: ImageLinks?
)

data class ImageLinks(
    val thumbnail: String
)
