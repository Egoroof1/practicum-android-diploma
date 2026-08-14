package ru.practicum.android.diploma.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.presentation.details.DetailScreen
import ru.practicum.android.diploma.presentation.favorites.FavoritesScreen
import ru.practicum.android.diploma.presentation.home.HomeScreen
import ru.practicum.android.diploma.presentation.team.TeamScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen()
            }
            composable(Screen.Profile.route) {
                TeamScreen()
            }

            // Нужно потом удалить это Test для details
            composable(Screen.Detail.route) {
                DetailScreen("0075d8dd-85a0-32ee-823c-17818e5b7b74",navController)
            }

            // Здесь делаем переход на details и передачу аргумента
//            composable(
//                route = Screen.Detail.route,
//                arguments = listOf(
//                    navArgument("itemId") { defaultValue = "" }
//                )
//            ) { backStackEntry ->
//                val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
//                DetailScreen(
//                    itemId = itemId,
//                    navController = navController
//                )
//            }
        }
    }
}
