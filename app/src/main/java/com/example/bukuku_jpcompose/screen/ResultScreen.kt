package com.example.bukuku_jpcompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.bukuku_jpcompose.model.request.BookItemRequest
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.response.ImageLinks
import com.example.bukuku_jpcompose.model.response.VolumeInfo
import com.example.bukuku_jpcompose.model.viewModel.BooksViewModel
import com.example.bukuku_jpcompose.navigation.Screen
import com.example.bukuku_jpcompose.utils.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun ResultScreen(
    id_book: String,
    title: String,
    description: String,
    imageUrl: String,
    navController: NavController,
    viewModel: BooksViewModel,
    userToken: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Text(title, style = MaterialTheme.typography.headlineMedium) }
        item {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = title,
                modifier = Modifier.fillMaxWidth().height(240.dp),
                contentScale = ContentScale.Crop
            )
        }
        item { Text(description, style = MaterialTheme.typography.bodyLarge) }
        item {
                Button(onClick = {
                    scope.launch {
                        val token = PreferenceManager.getToken(context)?: ""
                        val bookItem = BookItemRequest(id_book = id_book,title= title, description = description, thumbnail = imageUrl)
                        viewModel.addToCollection(bookItem, token)
                        navController.navigate(Screen.Collection.route)
                    }
                }) { Text("Add to Collection") }

        }
    }
}
