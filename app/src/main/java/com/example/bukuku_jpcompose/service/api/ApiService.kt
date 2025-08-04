package com.example.bukuku_jpcompose.service.api

import com.example.bukuku_jpcompose.model.request.LoginRequest
import com.example.bukuku_jpcompose.model.request.RegisterRequest
import com.example.bukuku_jpcompose.model.request.UpdateProfileRequest
import com.example.bukuku_jpcompose.model.response.LoginResponse
import com.example.bukuku_jpcompose.model.response.RegisterResponse
import com.example.bukuku_jpcompose.model.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    // ✅ Ambil profile user
    @GET("api/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Response<UserResponse>

    // ✅ Update profile user
    @PUT("api/user/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): Response<UserResponse>

}