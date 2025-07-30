package com.example.bukuku_jpcompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bukuku_jpcompose.screen.CollectionScreen
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

        // Halaman Result, membutuhkan parameter "title"
        composable(
            route = "result/{title}/{desc}/{image}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("desc") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val desc = backStackEntry.arguments?.getString("desc") ?: ""
            val image = backStackEntry.arguments?.getString("image") ?: ""
            ResultScreen(title = title, description = desc, imageUrl = image, navController = navController)
        }

//        composable(
//            route = Screen.Result.route,
//            arguments = listOf(navArgument("title") {
//                type = NavType.StringType // Tipe parameter: String
//            })
//        ) {
//            // Mengambil argumen "text" dari route dan mengirim ke ResultScreen
//            val  title = it.arguments?.getString("title") ?: ""
//            ResultScreen(it.arguments?.getString("title").toString(), navController)
//        }

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

        //Halaman CollectionScreen
        composable(Screen.Collection.route) {
            CollectionScreen(navController)
        }


    }
}