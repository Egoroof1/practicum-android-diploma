package ru.practicum.android.diploma.domain.filter.impl

import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.VacancyFilter

class FilterInteractorImpl(private val filterRepository: FilterRepository) : FilterInteractor {

    override fun setVacancyFilter(vacancyFilter: VacancyFilter) {
        filterRepository.setVacancyFilter(vacancyFilter)
    }

    override fun clearVacancyFilter() {
        filterRepository.clearVacancyFilter()
    }

    override fun getVacancyFilter(): VacancyFilter {
        return filterRepository.getVacancyFilter()
    }
}
