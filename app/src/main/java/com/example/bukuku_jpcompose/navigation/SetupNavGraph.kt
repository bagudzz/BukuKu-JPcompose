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
import com.example.bukuku_jpcompose.model.viewModel.BooksViewModel

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userToken: String,
    viewModel: BooksViewModel
) {
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
            route = "result/{id_book}/{title}/{desc}/{image}",
            arguments = listOf(
                navArgument("id_book") { type = NavType.StringType},
                navArgument("title") { type = NavType.StringType },
                navArgument("desc") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val idbook = backStackEntry.arguments?.getString("id_book") ?: ""
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val desc = backStackEntry.arguments?.getString("desc") ?: ""
            val image = backStackEntry.arguments?.getString("image") ?: ""
            ResultScreen(
                id_book = idbook,
                title = title,
                description = desc,
                imageUrl = image,
                navController = navController,
                viewModel = viewModel,
                userToken = userToken)
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

        //Halaman CollectionScreen
        composable(Screen.Collection.route) {
            CollectionScreen(
                navController = navController,
                viewModel = viewModel,
                userToken = userToken
            )
        }


    }
}