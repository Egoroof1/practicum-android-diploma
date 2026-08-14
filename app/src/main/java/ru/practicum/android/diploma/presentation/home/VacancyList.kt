package ru.practicum.android.diploma.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.ui.components.VacancyItem
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun VacancyList(
    vacancies: List<VacancyShort>,
    onVacancyClick: (String) -> Unit,
    onLoadNextPage: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    isNextPageLoading: Boolean = false,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = contentPadding,
    ) {
        itemsIndexed(items = vacancies, key = { _, vacancy -> vacancy.id }) { index, vacancy ->
            if (index == vacancies.lastIndex) {
                LaunchedEffect(vacancies.size) { onLoadNextPage() }
            }
            VacancyItem(
                vacancy = vacancy,
                onClick = { onVacancyClick(vacancy.id) },
            )
        }
        if (isNextPageLoading) {
            item { NextPageLoader() }
        }
    }
}

@Composable
private fun NextPageLoader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.Spacing16),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Dimens.ProgressSize),
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(name = "Список вакансий", showBackground = true)
@Composable
private fun VacancyListPreview() {
    AppTheme {
        VacancyList(
            vacancies = listOf(
                VacancyShort(
                    id = "1",
                    name = "Android-разработчик",
                    city = "Москва",
                    company = "Еда",
                    salaryFrom = 100_000,
                    salaryTo = null,
                    salaryCurrency = "RUR",
                    logo = null,
                ),
            ),
            onVacancyClick = {},
            onLoadNextPage = {},
            isNextPageLoading = true,
        )
    }
}
