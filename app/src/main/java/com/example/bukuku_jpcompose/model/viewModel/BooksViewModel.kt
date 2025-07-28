package com.example.bukuku_jpcompose.model.viewModel

import android.util.Log
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
}
