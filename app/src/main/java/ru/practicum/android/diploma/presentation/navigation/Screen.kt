package ru.practicum.android.diploma.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Profile : Screen("profile")

    data object Detail : Screen("detail/{itemId}") {
        fun passId(itemId: String): String = "detail/$itemId"
    }
}
