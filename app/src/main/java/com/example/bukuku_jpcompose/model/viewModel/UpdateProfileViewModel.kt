package com.example.bukuku_jpcompose.model.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bukuku_jpcompose.service.api.ApiClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class UpdateProfileViewModel: ViewModel() {
    private val api = ApiClient.instance
    var messageState = mutableStateOf("")
        private set


    fun updateProfile(token: String, nmLengkap: String, email: String, username: String, imageFile: File?) {
        viewModelScope.launch {
            try {
                // ✅ Convert field text ke RequestBody
                val nmRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), nmLengkap)
                val emailRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
                val usernameRequest = RequestBody.create("text/plain".toMediaTypeOrNull(), username)

                // ✅ Convert foto jika ada
                val fotoPart = imageFile?.let {
                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), it)
                    MultipartBody.Part.createFormData("foto", it.name, requestFile)
                }

                // ✅ Call API
                val response = api.updateProfile("Bearer $token", nmRequest, emailRequest, usernameRequest, fotoPart)

                if (response.isSuccessful) {
                    messageState.value = "✅ Profile updated successfully"
                } else {
                    messageState.value = "❌ Update failed: ${response.message()}"
                }
            } catch (e: Exception) {
                messageState.value = "⚠️ Error: ${e.message}"
            }
        }
    }
}
