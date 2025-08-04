package com.example.bukuku_jpcompose.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.bukuku_jpcompose.model.viewModel.ProfileViewModel
import com.example.bukuku_jpcompose.navigation.Screen
import com.example.bukuku_jpcompose.utils.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Observe data user dari ViewModel
    val userState by viewModel.userState.collectAsState()
    val messageState by viewModel.messageState.collectAsState()

    val username = userState?.username ?: "User Name"
    val email = userState?.email ?: "user@email.com"
    val profileUrl = userState?.profileUrl ?: "https://via.placeholder.com/150"

    LaunchedEffect(Unit) {
        val token = PreferenceManager.getToken(context) ?: ""
        if (token.isNotEmpty()) {
            viewModel.fetchUserProfile(token)  // API dipanggil
        }
    }

    if (userState == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()  // ✅ tampilkan loading spinner
        }
        return
    }

    // Layout Profile
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ✅ Foto Profil default
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF673AB7), Color(0xFF9C27B0)) // Ungu tua → ungu muda
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = rememberAsyncImagePainter(profileUrl),
                contentDescription = "Foto Profil",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Data User
        Text(text = username, style = MaterialTheme.typography.titleLarge)
        Text(text = email, style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ Tombol Update Profile
        Button(
            onClick = {
                // TODO: Panggil API update profile
                 navController.navigate(Screen.Update.route)
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
        // Pesan jika ada error
        if (messageState.isNotEmpty()) {
            Text(
                text = messageState,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}
