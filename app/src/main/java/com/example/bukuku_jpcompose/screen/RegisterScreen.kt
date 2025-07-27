package com.example.bukuku_jpcompose.screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.bukuku_jpcompose.R
import com.example.bukuku_jpcompose.model.request.RegisterRequest
import com.example.bukuku_jpcompose.navigation.Screen
import com.example.bukuku_jpcompose.service.api.ApiClient
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavHostController) {
    //colour pallete
    val LightBlue = Color(0xFF0096c7)
    val SoftBlue = Color(0xFFf8f9fa)

    // State untuk nilai input dari form registrasi
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // State untuk menandai apakah input mengalami kesalahan validasi
    var fullNameError by remember { mutableStateOf(false) }
    var usernameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

    // State untuk menampilkan indikator loading selama proses registrasi
    var isLoading by remember { mutableStateOf(false) }

    // Utilitas fokus input dan konteks aplikasi
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SoftBlue),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        // Tata letak utama: kolom berisi input dan tombol
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .imePadding(), // Menyesuaikan layout agar tidak tertutup keyboard
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "Silahkan mengisi data diri anda",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = LightBlue
            )
            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // Input: Nama Lengkap
            OutlinedTextField(
                value = fullName,
                onValueChange = {
                    fullName = it
                    fullNameError = false
                },
                isError = fullNameError,
                label = { Text("Nama Lengkap") },
                modifier = Modifier.fillMaxWidth()
            )
            if (fullNameError) {
                Text(
                    "Nama lengkap wajib diisi",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input: Username
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = false
                },
                isError = usernameError,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            if (usernameError) {
                Text(
                    "Username wajib diisi",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input: Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                isError = emailError,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            if (emailError) {
                Text(
                    "Email tidak valid",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input: Password
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                isError = passwordError,
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (passwordError) {
                Text(
                    "Password wajib diisi",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Input: Konfirmasi Password
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = false
                },
                isError = confirmPasswordError,
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            if (confirmPasswordError) {
                Text(
                    "Password tidak cocok",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tombol Register: memvalidasi form dan mengirim data
            Button(
                onClick = {
                    focusManager.clearFocus()

                    // Validasi input sebelum submit
                    fullNameError = fullName.isBlank()
                    usernameError = username.isBlank()
                    emailError = email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                    passwordError = password.isBlank()
                    confirmPasswordError = confirmPassword != password

                    // Jika semua input valid, lakukan registrasi
                    if (!fullNameError && !usernameError && !emailError && !passwordError && !confirmPasswordError) {
                        isLoading = true
                        coroutineScope.launch {
                            try {
                                val response = ApiClient.instance.register(
                                    RegisterRequest(
                                        nm_lengkap = fullName,
                                        email = email,
                                        username = username,
                                        password = password
                                    )
                                )
                                isLoading = false
                                val body = response.body()

                                if (response.isSuccessful && body != null) {
                                    Toast.makeText(
                                        context,
                                        "Registrasi berhasil!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Navigasi ke halaman login setelah sukses
                                    navController.navigate(Screen.Login.route) {
                                        popUpTo(Screen.Register.route) { inclusive = true }
                                    }
                                } else {
                                    val errorMsg = body?.message ?: response.message()
                                    Toast.makeText(context, "Gagal: $errorMsg", Toast.LENGTH_LONG)
                                        .show()
                                }
                            } catch (e: Exception) {
                                isLoading = false
                                Toast.makeText(
                                    context,
                                    "Error: ${e.localizedMessage}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Navigasi ke halaman login jika sudah punya akun
            TextButton(
                onClick = { navController.navigate(Screen.Login.route) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sudah punya akun? Login")
            }
        }

        // Dialog loading selama proses registrasi berlangsung
        if (isLoading) {
            AlertDialog(
                onDismissRequest = {}, // Tidak dapat ditutup manual
                confirmButton = {},
                title = { Text("Mohon tunggu") },
                text = {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("Sedang mengirim data...")
                    }
                }
            )
        }
    }
}