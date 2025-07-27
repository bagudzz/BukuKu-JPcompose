package com.example.bukuku_jpcompose.navigation

/**
 * Kelas sealed `Screen` berfungsi sebagai representasi rute (route) atau nama halaman
 * yang digunakan dalam sistem navigasi Compose.
 *
 * Setiap objek di dalam sealed class ini merepresentasikan satu layar atau halaman.
 */
sealed class Screen(val route: String) {

    /**
     * Objek untuk halaman Home.
     * Route-nya adalah "home".
     */
    object Home : Screen(route = "home")

    /**
     * Objek untuk halaman Result.
     * Route-nya memiliki argumen dinamis "text".
     * Misalnya: "result/HaloDunia"
     */
    object Result : Screen(route = "result/{text}") {
        /**
         * Fungsi untuk membuat route lengkap dengan nilai parameter.
         *
         * @param text nilai teks yang ingin dikirim ke halaman Result.
         * @return route lengkap seperti "result/isiText".
         */
        fun passText(text: String): String {
            return "result/$text"
        }
    }

    /**
     * Objek untuk halaman Profile.
     * Route-nya adalah "profile".
     */
    object Profile : Screen(route = "profile")

    object Login : Screen("login")
    object Register : Screen("register")
}