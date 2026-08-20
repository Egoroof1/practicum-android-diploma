package ru.practicum.android.diploma.presentation.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.home.VacancyList
import ru.practicum.android.diploma.presentation.navigation.Screen
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.components.ScreenPlaceholder
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun FavoritesScreen(
    navController: NavController,
    viewModel: FavoritesViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesContent(
        state = state,
        onVacancyClick = { vacancyId -> navController.navigate(Screen.Detail.passId(vacancyId)) },
    )
}

@Composable
private fun FavoritesContent(
    state: FavoritesState,
    onVacancyClick: (String) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { AppTopBar(title = stringResource(id = R.string.favorites)) },
    ) { innerPadding ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        when (state) {
            is FavoritesState.Content -> VacancyList(
                vacancies = state.vacancies,
                onVacancyClick = onVacancyClick,
                onLoadNextPage = {},
                modifier = contentModifier,
            )

            FavoritesState.Empty -> ScreenPlaceholder(
                imageRes = R.drawable.il_empty_list,
                modifier = contentModifier,
                text = stringResource(id = R.string.placeholder_empty_list),
            )

            FavoritesState.Error -> ScreenPlaceholder(
                imageRes = R.drawable.il_nothing_found,
                modifier = contentModifier,
                text = stringResource(id = R.string.placeholder_load_failed),
            )

            FavoritesState.Loading -> Box(
                modifier = contentModifier,
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.ProgressSize),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}
