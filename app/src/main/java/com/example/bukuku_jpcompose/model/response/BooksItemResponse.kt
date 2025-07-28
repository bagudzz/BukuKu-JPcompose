package com.example.bukuku_jpcompose.model.response

data class BooksResponse(
    val items: List<BookItem>? = null
)

data class BookItem(
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
