package ru.practicum.android.diploma.presentation.favorites

import ru.practicum.android.diploma.domain.models.VacancyShort

sealed interface FavoritesState {
    data object Loading : FavoritesState
    data object Empty : FavoritesState
    data object Error : FavoritesState
    data class Content(val vacancies: List<VacancyShort>) : FavoritesState
}
