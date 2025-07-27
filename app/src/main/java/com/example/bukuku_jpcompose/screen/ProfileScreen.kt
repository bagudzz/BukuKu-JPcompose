package com.example.bukuku_jpcompose.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// Halaman Profile sederhana
@Composable
fun ProfileScreen(navController: NavController) {
    Text("Ini Profile Screen") // Menampilkan teks statis
}

// Preview halaman Profile di Android Studio
@Composable
@Preview(showBackground = true)
fun PrfileScreenView() {
    ProfileScreen(
        navController = rememberNavController() // Controller dummy hanya untuk preview
    )
}