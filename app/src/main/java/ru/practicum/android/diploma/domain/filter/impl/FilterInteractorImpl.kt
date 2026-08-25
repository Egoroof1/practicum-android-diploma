package ru.practicum.android.diploma.domain.filter.impl

import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.VacancyFilter

class FilterInteractorImpl(private val filterRepository: FilterRepository) : FilterInteractor {
    override fun setIndustryFilter(industry: Industry?) {
        filterRepository.setIndustryFilter(industry)
    }

    override fun removeIndustryFilter() {
        filterRepository.removeIndustryFilter()
    }

    override fun setWithSalaryFilter(isEnable: Boolean) {
        filterRepository.setWithSalaryFilter(isEnable)
    }

    override fun removeWithSalaryFilter() {
        filterRepository.removeWithSalaryFilter()
    }

    override fun setMinSalaryFilter(salary: String) {
        filterRepository.setMinSalaryFilter(salary)
    }

    override fun removeMinSalaryFilter() {
        filterRepository.removeMinSalaryFilter()
    }

    override fun clearAllFilters() {
        filterRepository.clearAllFilters()
    }

    override fun getFilterParams(): VacancyFilter {
        return filterRepository.getFilterParams()
    }
}
