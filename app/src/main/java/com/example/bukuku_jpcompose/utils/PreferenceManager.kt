package com.example.bukuku_jpcompose.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

/**
 * Object `PreferenceManager` merupakan utilitas untuk mengelola penyimpanan data sederhana
 * secara persisten menggunakan Jetpack `PreferenceDataStore`.
 *
 * Saat ini, `PreferenceManager` digunakan untuk menyimpan dan mengambil token autentikasi
 * pengguna yang diperlukan untuk mengakses endpoint API yang bersifat privat.
 *
 * Fitur utama:
 * - Penyimpanan token autentikasi dalam format String.
 * - Pengambilan token yang sebelumnya disimpan dari DataStore.
 * - Menggunakan coroutine (`suspend function`) untuk operasi asynchronous dan non-blocking.
 *
 * Konteks penggunaan:
 * Digunakan di dalam ViewModel atau Composable function untuk menyimpan token setelah login,
 * atau mengambil token saat membuat permintaan yang membutuhkan autentikasi.
 *
 * Contoh penyimpanan:
 * ```
 * viewModelScope.launch {
 *     PreferenceManager.setToken(context, token)
 * }
 * ```
 *
 * Contoh pembacaan:
 * ```
 * viewModelScope.launch {
 *     val token = PreferenceManager.getToken(context)
 * }
 * ```
 */
object PreferenceManager {

    // Inisialisasi DataStore dengan nama file "user_prefs"
    private val Context.dataStore by preferencesDataStore("user_prefs")

    // Key untuk menyimpan token autentikasi
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    /**
     * Menyimpan token autentikasi ke dalam DataStore.
     *
     * @param context Konteks aplikasi yang digunakan untuk mengakses DataStore.
     * @param token Token yang akan disimpan.
     */
    suspend fun setToken(context: Context, token: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    /**
     * Mengambil token autentikasi yang telah disimpan dari DataStore.
     *
     * @param context Konteks aplikasi untuk mengakses DataStore.
     * @return Token dalam bentuk String, atau null jika belum tersimpan.
     */
    suspend fun getToken(context: Context): String? {
        val prefs = context.dataStore.data.first()
        return prefs[TOKEN_KEY]
    }
}