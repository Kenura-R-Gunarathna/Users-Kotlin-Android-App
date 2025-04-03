package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.myapplication.ui.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.viewModels.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val userViewModel = UserViewModel(application = application)
                MainScreen(userViewModel)
            }
        }
    }
}

@Composable
fun MainScreen(userViewModel: UserViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "view_users_screen"
    ) {
        composable("add_user_screen") {
            UserEntryScreen(
                viewModel = userViewModel,
                onNavigateToList = { navController.navigate("view_users_screen") }
            )
        }
        composable("view_users_screen") {
            UserListScreen(
                viewModel = userViewModel,
                onNavigateToEdit = { user -> navController.navigate("edit_user_screen/${user.id}") },
                onNavigateToAddUser = { navController.navigate("add_user_screen") }
            )
        }
        composable(
            "edit_user_screen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: -1

            // Use collectAsState() to observe the users state
            val users by userViewModel.users.collectAsState()

            // Find the user based on the userId
            val user = users.find { it.id == userId }

            // If the user is found, show the UserEditScreen
            user?.let {
                UserEditScreen(it, userViewModel) {
                    navController.popBackStack()
                }
            }
        }
    }
}
