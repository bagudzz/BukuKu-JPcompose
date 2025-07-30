package com.example.bukuku_jpcompose.model.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.service.api.BooksApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class BooksViewModel : ViewModel() {
    private val _books = MutableStateFlow<List<BookItem>>(emptyList())
    val books: StateFlow<List<BookItem>> = _books

    init {
        fetchBooks()
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            try {
                val response = BooksApiClient.instance.getBooks()
                Log.d("BooksViewModel", "API success, items = ${response.items?.size ?: 0}")
                _books.value = response.items ?: emptyList()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("BooksViewModel", "Fetch books failed: ${e.message}")
                _books.value = emptyList() // fallback kalau API gagal
            }
        }
    }

    // Koleksi buku favorit (bisa dari mutableStateList atau LiveData)
    private val _collection = mutableStateListOf<BookItem>()
    val collection: List<BookItem> get() = _collection
    val collectionState: State<List<BookItem>> get() = derivedStateOf { _collection }

    fun addToCollection(book: BookItem) {
        if (!_collection.contains(book)) {
            _collection.add(book)
        }
    }

    fun removeFromCollection(book: BookItem) {
        _collection.remove(book)
    }
}