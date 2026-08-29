package ru.practicum.android.diploma.domain.filter

import ru.practicum.android.diploma.domain.models.VacancyFilter

interface FilterRepository {
    fun setVacancyFilter(vacancyFilter: VacancyFilter)
    fun clearVacancyFilter()
    fun getVacancyFilter(): VacancyFilter
}
