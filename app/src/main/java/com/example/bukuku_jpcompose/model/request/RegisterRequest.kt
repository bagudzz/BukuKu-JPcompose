package com.example.bukuku_jpcompose.model.request

data class RegisterRequest(
    val nm_lengkap: String,
    val email: String,
    val username: String,
    val password: String,
)