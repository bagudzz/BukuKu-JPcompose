package com.example.bukuku_jpcompose.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 26.dp)
    ) {
        // Tampilan sementara (placeholder kosong)
        Text(
            text = "Selamat datang di Home Screen!",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
// Preview untuk menampilkan tampilan HomeScreen di Android Studio
@Composable
@Preview(showBackground = true)
fun HomeScreenView() {
    HomeScreen(
        navController = rememberNavController() // Dummy navController untuk preview
    )
}