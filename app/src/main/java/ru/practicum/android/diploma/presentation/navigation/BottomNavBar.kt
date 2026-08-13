package ru.practicum.android.diploma.presentation.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.Gray

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Team
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .height(80.dp)
            .drawBehind {
                drawLine(
                    color = Gray,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.dp.toPx()
                )
            }
    ) {
        items.forEach { item ->
            val isSelected = currentDestination == item.route
            val tintColor = if (isSelected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }

            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = null,
                        tint = tintColor
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = item.titleResId),
                        color = tintColor
                    )
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                )
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
