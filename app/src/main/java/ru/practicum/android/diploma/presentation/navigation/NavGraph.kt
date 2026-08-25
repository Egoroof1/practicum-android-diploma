package ru.practicum.android.diploma.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.presentation.details.DetailScreen
import ru.practicum.android.diploma.presentation.favorites.FavoritesScreen
import ru.practicum.android.diploma.presentation.filter.FilterScreen
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.filter.IndustryScreen
import ru.practicum.android.diploma.presentation.home.HomeScreen
import ru.practicum.android.diploma.presentation.team.TeamScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    viewModel: FilterViewModel = koinViewModel()
) {
    // Получаем текущий маршрут
    val currentRoute by navController.currentBackStackEntryAsState()
    val currentDestination = currentRoute?.destination?.route

    val stateFilter by viewModel.state.collectAsStateWithLifecycle()
    val isFilterActive =
        (stateFilter.selectedIndustry != null || stateFilter.selectedSalary.isNotEmpty() || stateFilter.isOnlyWithSalary)

    // Определяем, нужно ли показывать BottomBar
    val showBottomBar = when (currentDestination) {
        Screen.Home.route,
        Screen.Favorites.route,
        Screen.Profile.route -> true

        else -> false
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            if (showBottomBar) {
                BottomNavBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    isFilterActive = isFilterActive,
                    onFilterClick = { navController.navigate(Screen.Filter.route) },
                )
            }
            composable(Screen.Filter.route) {
                FilterScreen(
                    onBackClick = {
                        viewModel.resetToLastAppliedFilter()
                        navController.navigateUp()
                    },
                    onIndustryClick = { navController.navigate(Screen.Industry.route) },
                    onApplyClick = {
                        viewModel.setFilter()
                        navController.navigateUp()
                    },
                    viewModel = viewModel
                )
            }
            composable(Screen.Industry.route) {

                IndustryScreen(
                    onBackClick = {
                        navController.navigateUp()
                        viewModel.updateSearchQuery("")
                    },
                    onChooseClick = {
                        navController.navigateUp()
                        viewModel.updateSearchQuery("")
                    },
                    viewModel = viewModel
                )
            }
            composable(Screen.Favorites.route) {
                FavoritesScreen(navController)
            }
            composable(Screen.Profile.route) {
                TeamScreen()
            }

            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("itemId") { defaultValue = "" }
                )
            ) { backStackEntry ->
                val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
                DetailScreen(
                    itemId = itemId,
                    navController = navController
                )
            }
        }
    }
}
