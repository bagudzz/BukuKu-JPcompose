package com.example.bukuku_jpcompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.viewModel.BooksViewModel

@Composable
fun HomeScreen(navController: NavController,
               viewModel: BooksViewModel = viewModel()) {
                    val books by viewModel.books.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 26.dp)
    ) {
        if (books.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(books) { book ->
                    BookItemView(book)
                }
            }
        }
    }
}

@Composable
fun BookItemView(book: BookItem) {
    val info = book.volumeInfo
    val title = info?.title ?: "Tidak ada judul"
    val description = info?.description ?: "Tidak ada deskripsi"
    val thumbnail = info?.imageLinks?.thumbnail
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(info.imageLinks?.thumbnail),
                contentDescription = info.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(text = info.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = info.description ?: "Tidak ada deskripsi",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3
                )
            }
        }
    }
}
