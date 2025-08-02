package com.example.bukuku_jpcompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.bukuku_jpcompose.utils.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // State data user (dari API nanti diisi oleh ViewModel)
    var username by remember { mutableStateOf("User Name") }
    var email by remember { mutableStateOf("user@email.com") }
    var profileUrl by remember { mutableStateOf("https://via.placeholder.com/150") }

    // Layout Profile
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ Foto Profil
        Image(
            painter = rememberAsyncImagePainter(profileUrl),
            contentDescription = "Foto Profil",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Data User
        Text(text = username, style = MaterialTheme.typography.titleLarge)
        Text(text = email, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ Tombol Update Profile
        Button(
            onClick = {
                // TODO: Panggil API update profile
                // navController.navigate("update_profile")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Profile")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ✅ Tombol Logout
        Button(
            onClick = {
                scope.launch {
                    PreferenceManager.setToken(context, "") // hapus token
                    navController.navigate("login") { popUpTo(0) } // kembali ke login
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
