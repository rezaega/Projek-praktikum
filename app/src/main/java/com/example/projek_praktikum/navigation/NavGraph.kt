package com.example.projek_praktikum.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.projek_praktikum.model.viewModel.ViewModelDana
import com.example.projek_praktikum.screen.DetailPenggunaanDanaScreen
import com.example.projek_praktikum.screen.HomeScreen
import com.example.projek_praktikum.screen.LoginScreen
import com.example.projek_praktikum.screen.RegisterScreen
import com.example.projek_praktikum.screen.InputPenggunaanDana

@Composable
fun AppNavGraph(navController: NavHostController, viewModelDana: ViewModelDana) {
    NavHost(navController, startDestination = "login") {
        composable("home") {
            HomeScreen(navController, viewModelDana)
        }
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("input penggunaan") {
            InputPenggunaanDana(navController, viewModelDana)
        }
        composable("detail penggunaan") {
            DetailPenggunaanDanaScreen(
                viewModelDana = viewModelDana,
                navController = navController
            )
        }
        composable(
            route = "editPenggunaan/{id}/{nominal}/{deskripsi}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("nominal") { type = NavType.IntType },
                navArgument("deskripsi") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val nominal = backStackEntry.arguments?.getInt("nominal") ?: 0
            val deskripsi = backStackEntry.arguments?.getString("deskripsi") ?: ""

            InputPenggunaanDana(
                navController = navController,
                viewModelDana = viewModelDana,
                id = id,
                initNominal = nominal,
                initDeskripsi = deskripsi,
                isEdit = true
            )
        }

    }
}