package ru.practicum.android.diploma.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.R

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Team
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null
                    )
                },
                label = {
                    stringResource(id = item.titleResId)
                },
                selected = currentDestination == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

// Данные для пунктов меню
sealed class BottomNavItem(
    val route: String,
    val titleResId: Int,
    val icon: Int
) {
    data object Home : BottomNavItem(
        route = Screen.Home.route,
        titleResId = R.string.home,
        icon = R.drawable.ic_home_24px
    )

    data object Favorites : BottomNavItem(
        route = Screen.Favorites.route,
        titleResId = R.string.favorites,
        icon = R.drawable.ic_favorites_on_24px
    )

    data object Team : BottomNavItem(
        route = Screen.Profile.route,
        titleResId = R.string.team,
        icon = R.drawable.ic_team_24px
    )
}
