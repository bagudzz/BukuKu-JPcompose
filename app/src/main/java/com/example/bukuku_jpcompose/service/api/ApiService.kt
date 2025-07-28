package com.example.bukuku_jpcompose.service.api

import com.example.bukuku_jpcompose.model.request.LoginRequest
import com.example.bukuku_jpcompose.model.request.RegisterRequest
import com.example.bukuku_jpcompose.model.response.LoginResponse
import com.example.bukuku_jpcompose.model.response.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    /**
     * Mengirimkan permintaan registrasi pengguna baru ke server.
     *
     * @param request Objek [RegisterRequest] yang berisi email, username, dan password.
     * @return Objek [Response]<[RegisterResponse]> berisi hasil registrasi dari server.
     */
    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

    /**
     * Melakukan login dengan mengirimkan data kredensial pengguna ke server.
     *
     * @param request Objek [LoginRequest] berisi username dan password.
     * @return Objek [Response]<[LoginResponse]> yang berisi token autentikasi dan data pengguna.
     */
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}