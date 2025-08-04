package com.example.bukuku_jpcompose.model.request

data class UpdateProfileRequest(
    val nm_lengkap: String,
    val email: String,
    val username: String,
    val foto: String? = null
)