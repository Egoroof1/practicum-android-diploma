package ru.practicum.android.diploma.presentation.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.home.state.HomeState
import ru.practicum.android.diploma.ui.components.AppBarIcon
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.components.ResultCountChip
import ru.practicum.android.diploma.ui.components.ScreenPlaceholder
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState = HomeState.Loading,
) {
    var query by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { HomeTopBar() },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchField(
                query = query,
                onQueryChange = { newQuery -> query = newQuery },
                modifier = Modifier.padding(
                    horizontal = Dimens.Spacing16,
                    vertical = Dimens.Spacing8,
                ),
            )
            HomeChip(state = state)
            HomeBody(state = state)
        }
    }
}

@Composable
private fun HomeTopBar() {
    AppTopBar(
        title = stringResource(id = R.string.title_main),
        actions = {
            AppBarIcon(
                iconRes = R.drawable.ic_filter_off_24px,
                contentDescription = stringResource(id = R.string.description_filter),
                onClick = { },
            )
        },
    )
}

@Composable
private fun HomeChip(state: HomeState) {
    val text = when (state) {
        HomeState.Results -> pluralStringResource(
            id = R.plurals.vacancies_found,
            count = DEMO_VACANCY_COUNT,
            DEMO_VACANCY_COUNT,
        )
        HomeState.NotFound -> stringResource(id = R.string.vacancies_not_found)
        else -> null
    }
    if (text != null) {
        ResultCountChip(
            text = text,
            modifier = Modifier.padding(
                top = Dimens.Spacing3,
                bottom = Dimens.Spacing8,
            ),
        )
    }
}

@Composable
private fun HomeBody(state: HomeState) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            HomeState.Empty -> ScreenPlaceholder(imageRes = R.drawable.il_search_start)

            HomeState.Loading -> CircularProgressIndicator(
                modifier = Modifier.size(Dimens.ProgressSize),
                color = MaterialTheme.colorScheme.primary,
            )

            HomeState.NoInternet -> ScreenPlaceholder(
                imageRes = R.drawable.il_no_internet,
                text = stringResource(id = R.string.placeholder_no_internet),
            )

            HomeState.NotFound -> ScreenPlaceholder(
                imageRes = R.drawable.il_nothing_found,
                text = stringResource(id = R.string.placeholder_load_failed),
            )

            HomeState.Results -> VacancyList(
                vacancies = demoVacancies,
                onVacancyClick = {},
            )
        }
    }
}

@Preview(name = "Главная: пусто")
@Preview(name = "Главная: пусто (тёмная)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HomeScreenEmptyPreview() {
    AppTheme {
        HomeScreen(navController = rememberNavController(), state = HomeState.Empty)
    }
}

@Preview(name = "Главная: загрузка")
@Composable
private fun HomeScreenLoadingPreview() {
    AppTheme {
        HomeScreen(navController = rememberNavController(), state = HomeState.Loading)
    }
}

@Preview(name = "Главная: результаты")
@Composable
private fun HomeScreenResultsPreview() {
    AppTheme {
        HomeScreen(navController = rememberNavController(), state = HomeState.Results)
    }
}

@Preview(name = "Главная: не найдено")
@Composable
private fun HomeScreenNotFoundPreview() {
    AppTheme {
        HomeScreen(navController = rememberNavController(), state = HomeState.NotFound)
    }
}

@Preview(name = "Главная: нет интернета")
@Composable
private fun HomeScreenNoInternetPreview() {
    AppTheme {
        HomeScreen(navController = rememberNavController(), state = HomeState.NoInternet)
    }
}
