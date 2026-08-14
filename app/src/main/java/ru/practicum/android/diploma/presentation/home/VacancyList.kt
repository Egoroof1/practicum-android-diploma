package ru.practicum.android.diploma.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.ui.components.VacancyItem
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacancyList(
    vacancies: List<VacancyShort>,
    onVacancyClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = vacancies, key = { vacancy -> vacancy.id }) { vacancy ->
            VacancyItem(
                vacancy = vacancy,
                onClick = { onVacancyClick(vacancy.id) },
            )
        }
    }
}

@Preview(name = "Список вакансий", showBackground = true)
@Composable
private fun VacancyListPreview() {
    AppTheme {
        VacancyList(
            vacancies = demoVacancies,
            onVacancyClick = {},
        )
    }
}
