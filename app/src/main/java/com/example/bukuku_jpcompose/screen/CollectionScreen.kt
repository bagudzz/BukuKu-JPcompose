package com.example.bukuku_jpcompose.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.response.CollectionBook
import com.example.bukuku_jpcompose.model.viewModel.BooksViewModel
import com.example.bukuku_jpcompose.model.viewModel.BooksViewModelPreview
import com.example.bukuku_jpcompose.utils.PreferenceManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollectionScreen(
    navController: NavController,
    viewModel: BooksViewModel,
    userToken: String
) {
    // ✅ Ambil koleksi dari StateFlow (API atau lokal)
    val collection by viewModel.collectionState.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {

        val token = PreferenceManager.getToken(context)?: ""
        viewModel.fetchCollection(token)

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Koleksi Buku Favorit") }
            )
        }
    ) { padding ->
        if (collection.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
//                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(collection) { book ->
                    BookCard(
                        book = book,
                        onRemove = {
                        scope.launch {
                            val t = PreferenceManager.getToken(context) ?: ""
                            viewModel.deleteFromCollection(book.id_book ?: "", t)
                        }
                    },
                        onRead = {
                            //Navigasi ke ResultScreen menggunakan data dari koleksi
                            val idbook = Uri.encode(book.id_book)
                            val title = Uri.encode(book.title)
                            val desc = Uri.encode(book.description)
                            val img = Uri.encode(book.thumbnail ?: "")
                            navController.navigate("result/$idbook/$title/$desc/$img")
                        })
                }
            }
        }
    }
}

@Composable
fun BookCard(book: CollectionBook, onRemove: () -> Unit, onRead: () -> Unit) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(book.thumbnail),
                contentDescription = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onRemove,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Hapus Koleksi")
                }
                Button(
                    onClick = onRead,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Lanjut Baca")
                }
            }
        }
    }
}
