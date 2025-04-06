package com.example.artisan


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.artisan.domain.controller.UserViewModel
import com.example.artisan.screens.LoginScreen
import com.example.artisan.screens.HomeScreen
import com.example.artisan.screens.ProjectScreen
import kotlinx.serialization.Serializable

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val userViewModel = UserViewModel()

    NavHost(
        navController = navController,
        startDestination = LoginScreen
    ) {
        composable<LoginScreen> {
            LoginScreen(navController = navController, userViewModel = userViewModel)
        }

        composable<HomeScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<HomeScreen>()
            HomeScreen(navController = navController, email = args.email, password = args.password)
        }

        composable<ProjectScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<ProjectScreen>()
            ProjectScreen(email = args.email, projectId = args.projectId)
        }

    }
}

@Serializable
object LoginScreen

@Serializable
data class HomeScreen(val email: String, val password: String)

@Serializable
data class ProjectScreen(val email: String, val projectId: String)