package ru.practicum.android.diploma.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.ui.components.VacancyItemStub
import ru.practicum.android.diploma.ui.theme.AppTheme

@Composable
fun VacancyList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(demoVacancies, key = { it.id }) { vacancy ->
            VacancyItemStub(
                title = vacancy.title,
                employer = vacancy.employer,
                salary = vacancy.salary,
            )
        }
    }
}

@Preview(name = "Список вакансий", showBackground = true)
@Composable
private fun VacancyListPreview() {
    AppTheme {
        VacancyList()
    }
}
