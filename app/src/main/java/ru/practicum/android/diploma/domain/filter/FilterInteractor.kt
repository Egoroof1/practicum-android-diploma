package ru.practicum.android.diploma.domain.filter

import ru.practicum.android.diploma.domain.models.VacancyFilter

interface FilterInteractor {
    fun setVacancyFilter(vacancyFilter: VacancyFilter)
    fun clearVacancyFilter()
    fun getVacancyFilter(): VacancyFilter
}
