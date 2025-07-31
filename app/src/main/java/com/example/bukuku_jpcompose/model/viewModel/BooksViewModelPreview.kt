package com.example.bukuku_jpcompose.model.viewModel

import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.response.ImageLinks
import com.example.bukuku_jpcompose.model.response.VolumeInfo

/**
 * ViewModel khusus untuk Preview Compose.
 * Tidak memanggil API, hanya menggunakan data dummy.
 */
class BooksViewModelPreview : BooksViewModel() {

    init {
        // ✅ Tambahkan dummy data saat preview dimuat
        dummyCollection.forEach { addDummyBook(it) }
    }

    // ✅ Tambah dummy menggunakan fungsi protected dari parent
    private fun addDummyBook(book: BookItem) {
        addDummyInternal(book)
    }

    companion object {
        val dummyCollection = listOf(
            BookItem(
                id = "dummy_1",
                volumeInfo = VolumeInfo(
                    title = "Buku Dummy",
                    description = "Deskripsi buku dummy untuk preview.",
                    imageLinks = ImageLinks(
                        thumbnail = "https://via.placeholder.com/150"
                    )
                )
            )
        )
    }
}
