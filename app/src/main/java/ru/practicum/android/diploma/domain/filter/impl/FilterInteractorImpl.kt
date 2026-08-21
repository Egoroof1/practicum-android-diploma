package ru.practicum.android.diploma.domain.filter.impl

import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.Industry

class FilterInteractorImpl(private val filterRepository: FilterRepository) : FilterInteractor {
    override fun setIndustryFilter(industry: Industry) {
        filterRepository.setIndustryFilter(industry)
    }

    override fun getIndustryFilter(): Industry? {
       return filterRepository.getIndustryFilter()
    }

    override fun removeIndustryFilter() {
        filterRepository.removeIndustryFilter()
    }

    override fun setWithSalaryFilter(isEnable: Boolean) {
        filterRepository.setWithSalaryFilter(isEnable)
    }

    override fun getWithSalaryFilter(): Boolean {
        return filterRepository.getWithSalaryFilter()
    }

    override fun removeWithSalaryFilter() {
        filterRepository.removeWithSalaryFilter()
    }

    override fun setMinSalaryFilter(salary: Int) {
        filterRepository.setMinSalaryFilter(salary)
    }

    override fun getMinSalaryFilter(): Int {
        return filterRepository.getMinSalaryFilter()
    }

    override fun removeMinSalaryFilter() {
        filterRepository.removeMinSalaryFilter()
    }

    override fun clearAllFilters() {
        filterRepository.clearAllFilters()
    }
}
