package com.example.bukuku_jpcompose.model.response

data class RegisterResponse(
    val message: String,
    val user: RegisterUser
)

data class RegisterUser(
    val id: String,
    val email: String,
    val username: String
)