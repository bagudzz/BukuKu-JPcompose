package com.example.bukuku_jpcompose.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import com.example.bukuku_jpcompose.model.request.UpdateProfileRequest
import com.example.bukuku_jpcompose.model.viewModel.ProfileViewModel
import com.example.bukuku_jpcompose.utils.PreferenceManager
import kotlinx.coroutines.launch

@Composable
fun UpdateProfileScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val viewModel: ProfileViewModel = viewModel()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "Update Profile",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //Edit foto profile
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF2196F3),
                                Color(0xFF21CBF3)
                            ) // Biru tua → biru muda
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        selectedImageUri ?: "https://via.placeholder.com/150"
                    ),
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .clickable { launcher.launch("image/*") }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            //Form input
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        val token = PreferenceManager.getToken(context) ?: ""
                        if (token.isNotEmpty()) {
                            viewModel.updateProfile(
                                token,
                                UpdateProfileRequest(name, email, username)
                            )
                        }
                    }
                    message = "Profil berhasil diperbarui"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = message, color = MaterialTheme.colorScheme.primary)
        }
    }
}
@Composable
@Preview(showBackground = true)
fun UpdateProfilePreview() {
    UpdateProfileScreen(navController = rememberNavController())
}
