package com.example.bukuku_jpcompose.model.response

data class UserResponse(
    val code: Int,
    val message: String,
    val data: UserData
)

data class UserData(
    val id: String,
    val username: String,
    val email: String,
    val nm_lengkap:String,
    val profileUrl: String? = null
)