package com.example.bukuku_jpcompose.model.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bukuku_jpcompose.model.response.UserData
import com.example.bukuku_jpcompose.model.request.UpdateProfileRequest
import com.example.bukuku_jpcompose.service.api.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _userState = MutableStateFlow<UserData?>(null)
    val userState: StateFlow<UserData?> = _userState

    private val _messageState = MutableStateFlow("")
    val messageState: StateFlow<String> = _messageState

    // ✅ Ambil profile dari API
    fun fetchUserProfile(token: String) {
        viewModelScope.launch {
            try {
                val response = ApiClient.instance.getProfile("Bearer $token")
                if (response.isSuccessful) {
                    _userState.value = response.body()?.data
                } else {
                    _messageState.value = "Gagal memuat profil"
                }
            } catch (e: Exception) {
                _messageState.value = "Error: ${e.message}"
            }
        }
    }
}
