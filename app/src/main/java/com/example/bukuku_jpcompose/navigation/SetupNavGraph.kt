package com.example.bukuku_jpcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bukuku_jpcompose.screen.HomeScreen
import com.example.bukuku_jpcompose.screen.LoginScreen
import com.example.bukuku_jpcompose.screen.ProfileScreen
import com.example.bukuku_jpcompose.screen.RegisterScreen
import com.example.bukuku_jpcompose.screen.ResultScreen

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    // Membuat NavHost untuk mengatur semua rute
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route, // Halaman awal aplikasi
        modifier = modifier
    ) {

        // Halaman Home
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }

        // Halaman Result, membutuhkan parameter "text"
        composable(
            route = Screen.Result.route,
            arguments = listOf(navArgument("text") {
                type = NavType.StringType // Tipe parameter: String
            })
        ) {
            // Mengambil argumen "text" dari route dan mengirim ke ResultScreen
            ResultScreen(it.arguments?.getString("text").toString(), navController)
        }

        // Halaman Profile
        composable(
            route = Screen.Profile.route
        ) {
            ProfileScreen(navController)
        }

        // Halaman Login
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }

        // Halaman Register
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }


    }
}