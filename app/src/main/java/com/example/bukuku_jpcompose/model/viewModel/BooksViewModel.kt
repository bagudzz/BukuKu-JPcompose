package com.example.bukuku_jpcompose.model.viewModel

import android.media.session.MediaSession.Token
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bukuku_jpcompose.model.request.BookItemRequest
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.response.CollectionBook
import com.example.bukuku_jpcompose.service.api.BooksApiClient
import com.example.bukuku_jpcompose.service.api.CollectionApiClient
import com.example.bukuku_jpcompose.service.api.DeleteRequest
import com.example.bukuku_jpcompose.utils.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BooksViewModel : ViewModel() {

    // ✅ State koleksi buku
    private val _bukuState = MutableStateFlow<List<BookItem>>(emptyList())
    val bukuState: StateFlow<List<BookItem>> = _bukuState

    private val _collectionState = MutableStateFlow<List<CollectionBook>>(emptyList())
    val collectionState: StateFlow<List<CollectionBook>> = _collectionState

    private val _getCollectionMessageState = MutableStateFlow("")
    val getCollectionMessageState: StateFlow<String> = _getCollectionMessageState



    fun fetchBooks(query: String="") {
        viewModelScope.launch {
            try {
                val keyword = if (query.isBlank()) "all" else query
                val response = BooksApiClient.instance.getBooks(query)
                val items = response.items ?: emptyList()
                _bukuState.value = items
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchCollection(token:String) {
        viewModelScope.launch {
            try {
                val response = CollectionApiClient.instance.getUserCollection("Bearer $token")
                val items = response.body()?.data ?: emptyList()
                println("DEBUG: items: $items")
                _collectionState.value = items
            } catch (e: Exception) {

            }
        }
    }
    // ✅ Tambah buku (versi API)
    open fun addToCollection(book: BookItemRequest, userToken: String) {
        viewModelScope.launch {
            // 🔥 Panggil API untuk menambah buku (implementasi API call di sini)
            try {
                println("DEBUG: Call API addBookToCollection with token=$userToken book=$book")
                val response = CollectionApiClient.instance.addBookToCollection("Bearer $userToken", book)
                println("DEBUG: Response code=${response.code()} body=${response.body()}")
                if (response.isSuccessful){
                    fetchCollection(userToken)
                    _getCollectionMessageState.value =  "Buku Berhasil Ditambahkan"
                }else{
                    _getCollectionMessageState.value =  "Buku gagal ditambahkan"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("DEBUG: ERROR CALL API ${e.message}")
            }


        }
    }

    // ✅ Hapus buku dari koleksi
    open fun deleteFromCollection(bookId: String, userToken: String) {
        viewModelScope.launch {
            try {
                val tokenHeader = if (userToken.startsWith("Bearer")) userToken else "Bearer $userToken"
                println("DEBUG: Call API DELETE dengan token=$tokenHeader")

                val response = CollectionApiClient.instance.deleteBookFromCollection(
                    tokenHeader,
                    mapOf("id_book" to bookId)
                )

                println("DEBUG: Response DELETE code=${response.code()} body=${response.body()}")

                if (response.isSuccessful) {
                    _collectionState.value = _collectionState.value.filterNot { it.id_book == bookId }
                    _getCollectionMessageState.value = "Buku berhasil dihapus"
                } else {
                    _getCollectionMessageState.value = "Gagal menghapus buku (${response.code()})"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                println("DEBUG: ERROR CALL DELETE API ${e.message}")
                _getCollectionMessageState.value = "Error: ${e.message}"
            }
        }
    }


    // ✅ Fungsi protected untuk digunakan di Preview
    protected fun addDummyInternal(book: BookItem) {
        val currentList = _bukuState.value.toMutableList()
        currentList.add(book)
        _bukuState.value = currentList
    }
}
