package com.example.artisan


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.artisan.screens.LoginScreen
import kotlinx.serialization.Serializable

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = LoginScreen
    ) {
        composable<LoginScreen> {
            LoginScreen(navController)
        }

    }
}

@Serializable
object LoginScreen

