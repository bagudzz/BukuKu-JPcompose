package com.example.bukuku_jpcompose.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.viewModel.BooksViewModel

@Composable
fun HomeScreen(navController: NavController,
               viewModel: BooksViewModel = viewModel()) {

    val books by viewModel.bukuState.collectAsState()

    // State untuk Search
    var searchQuery by remember { mutableStateOf("") }

    // State untuk kategori
    val categoriesList = listOf("Semua", "Fiksi", "Science", "Novel", "Drama", "Komik", "Biografi")

    LaunchedEffect(Unit) {
        viewModel.fetchBooks("all")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 26.dp)
    ) {
        // ✅ Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.fetchBooks(it)},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Cari Judul Buku") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    if (searchQuery.isNotBlank()) {
                        viewModel.fetchBooks(searchQuery)
                    }
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Cari")
                }
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Categories Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            categoriesList.forEach { category ->
                Button(
                    onClick = {
                        val query = if (category == "Semua") "all" else category
                        viewModel.fetchBooks(query)
                    },
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .height(36.dp),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(category, color = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
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
                    BookItemView(book){
                        //navigasi ke halaman detail
                        val idbook = Uri.encode(book.id)
                        val title = Uri.encode(book.volumeInfo.title)
                        val desc = Uri.encode(book.volumeInfo.description ?: "Tidak ada deskripsi")
                        val img = Uri.encode(book.volumeInfo.imageLinks?.thumbnail ?: "")
                        navController.navigate("result/$idbook/$title/$desc/$img")
//                        navController.navigate("result/${Uri.encode(book.volumeInfo.title)}")
                    }
                }
            }
        }

    }
}

@Composable
fun BookItemView(book: BookItem, onClick: () -> Unit) {
    val info = book.volumeInfo
    val title = info.title ?: "Tidak ada judul"
    val description = info.description ?: "Tidak ada deskripsi"
    val thumbnailUrl = info.imageLinks?.thumbnail

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(thumbnailUrl ?: "https://via.placeholder.com/150")
            .crossfade(true)
            .build()
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(info.imageLinks?.thumbnail),
                contentDescription = info.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(90.dp)
                    .padding(end = 16.dp)
            )

            Column {
                Text(text = info.title, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = info.description ?: "Tidak ada deskripsi",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
        }
    }
}
