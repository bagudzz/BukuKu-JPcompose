package com.example.bukuku_jpcompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.bukuku_jpcompose.model.response.BookItem
import com.example.bukuku_jpcompose.model.response.ImageLinks
import com.example.bukuku_jpcompose.model.response.VolumeInfo
import com.example.bukuku_jpcompose.model.viewModel.BooksViewModel
import com.example.bukuku_jpcompose.navigation.Screen

@Composable
fun ResultScreen(
    title: String,
    description: String,
    imageUrl: String,
    navController: NavController,
    viewModel: BooksViewModel = viewModel(),
) {
    val bookItem = BookItem(
        volumeInfo = VolumeInfo(
            title = title,
            description = description,
            imageLinks = ImageLinks(thumbnail = imageUrl)
        )
    )

    val alreadyAdded = viewModel.collection.contains(bookItem)

    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )
        }

        item {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        item {
            if (!alreadyAdded) {
                Button(onClick = {
                    viewModel.addToCollection(bookItem)
                }) {
                    Text("Add to Collection")
                }
            } else {
                Button(onClick = {}, enabled = false) {
                    Text("Sudah Ditambahkan")
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate(Screen.Home.route) }) {
                Text("Kembali ke Beranda")
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun ResultPreview() {
    ResultScreen(
        title = "Contoh Judul",
        description = "Ini adalah deskripsi singkat dari buku contoh.",
        imageUrl = "",
        navController = rememberNavController()
    )
}

//// Halaman hasil yang menerima input nama dari HomeScreen
//@Composable
//fun ResultScreen(name: String, navController: NavController) {
//    // Box digunakan untuk menempatkan isi di tengah layar
//    Box(
//        modifier = Modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center
//    ) {
//        // Kolom vertikal berisi teks dan tombol
//        Column(modifier = Modifier.padding(16.dp)) {
//            // Menampilkan sapaan kepada pengguna
//            Text(
//                text = "Halo, $name!",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            // Tombol untuk kembali ke halaman Home
//            Button(
//                onClick = {
//                    navController.navigate(route = Screen.Home.route)
//                }
//            ) {
//                Text("Kembali")
//            }
//        }
//    }
//}
//
///**
// * Preview `ResultScreen` untuk tampilan di Android Studio Preview.
// * Menggunakan `rememberNavController` sebagai dummy NavController.
// */
//@Preview(showBackground = true)
//@Composable
//fun ResultPreview() {
//    ResultScreen(
//        name = "Preview User",
//        navController = rememberNavController()
//    )
//}
