package com.example.bukuku_jpcompose.model.response

data class LoginResponse(
    val code: Int,
    val message: String,
    val data: LoginData?,
    val token: String?
)

data class LoginData(
    val uuid: String,
    val fullName:String
)