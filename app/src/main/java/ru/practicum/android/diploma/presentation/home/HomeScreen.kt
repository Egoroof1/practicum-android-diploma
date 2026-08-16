package ru.practicum.android.diploma.presentation.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.navigation.Screen
import ru.practicum.android.diploma.ui.components.AppBarIcon
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.components.ResultCountChip
import ru.practicum.android.diploma.ui.components.ScreenPlaceholder
import ru.practicum.android.diploma.ui.components.SearchField
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    NextPageErrorToast(state = state)

    HomeContent(
        state = state,
        onQueryChange = { query -> viewModel.onQueryChange(query) },
        onSearch = { viewModel.onSearchClick() },
        onVacancyClick = { vacancyId -> navController.navigate(Screen.Detail.passId(vacancyId)) },
        onLoadNextPage = { viewModel.searchPlusPage() },
    )
}

@Composable
private fun NextPageErrorToast(state: HomeState) {
    val context = LocalContext.current
    val messageRes = when (state.error) {
        SearchError.NoInternet -> R.string.toast_no_internet
        SearchError.LoadFailed -> R.string.toast_error
        null -> null
    }
    LaunchedEffect(state.error) {
        if (messageRes != null && state.vacancies.isNotEmpty()) {
            Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun HomeContent(
    state: HomeState,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    onVacancyClick: (String) -> Unit,
    onLoadNextPage: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { HomeTopBar() },
    ) { innerPadding ->
        val density = LocalDensity.current
        var headerHeight by remember { mutableStateOf(0.dp) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            HomeBody(
                state = state,
                topPadding = headerHeight,
                onVacancyClick = onVacancyClick,
                onLoadNextPage = onLoadNextPage,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { size ->
                        headerHeight = with(density) { size.height.toDp() }
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SearchBand(
                    query = state.searchQuery,
                    onQueryChange = onQueryChange,
                    onSearch = onSearch,
                )
                HomeChip(state = state)
            }
        }
    }
}

@Composable
private fun SearchBand(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = Dimens.Spacing16, vertical = Dimens.Spacing8),
    ) {
        SearchField(
            query = query,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
        )
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
    val found = state.allVacanciesQuery ?: 0
    val text = when {
        state.searchQuery.isBlank() || state.isLoading -> null
        state.vacancies.isNotEmpty() -> pluralStringResource(
            id = R.plurals.vacancies_found,
            count = found,
            found,
        )

        state.error == SearchError.LoadFailed -> stringResource(id = R.string.vacancies_not_found)
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
private fun HomeBody(
    state: HomeState,
    topPadding: Dp,
    onVacancyClick: (String) -> Unit,
    onLoadNextPage: () -> Unit,
) {
    val belowHeader = Modifier
        .fillMaxSize()
        .padding(top = topPadding)

    when {
        state.vacancies.isNotEmpty() -> VacancyList(
            vacancies = state.vacancies,
            onVacancyClick = onVacancyClick,
            onLoadNextPage = onLoadNextPage,
            contentPadding = PaddingValues(top = topPadding),
            isNextPageLoading = state.isNextPageLoading,
        )

        state.searchQuery.isBlank() -> ScreenPlaceholder(
            imageRes = R.drawable.il_search_start,
            modifier = belowHeader,
        )

        state.isLoading -> Box(
            modifier = belowHeader,
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(Dimens.ProgressSize),
                color = MaterialTheme.colorScheme.primary,
            )
        }

        state.error == SearchError.NoInternet -> ScreenPlaceholder(
            imageRes = R.drawable.il_no_internet,
            modifier = belowHeader,
            text = stringResource(id = R.string.placeholder_no_internet),
        )

        state.error == SearchError.LoadFailed -> ScreenPlaceholder(
            imageRes = R.drawable.il_nothing_found,
            modifier = belowHeader,
            text = stringResource(id = R.string.placeholder_load_failed),
        )

        else -> Unit
    }
}
