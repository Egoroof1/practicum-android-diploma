package ru.practicum.android.diploma.domain.filter

import ru.practicum.android.diploma.domain.models.VacancyFilter
import ru.practicum.android.diploma.domain.models.Industry

interface FilterRepository {
    fun setIndustryFilter(industry: Industry)
    fun removeIndustryFilter()
    fun setWithSalaryFilter(isEnable: Boolean)
    fun removeWithSalaryFilter()
    fun setMinSalaryFilter(salary: Int)
    fun removeMinSalaryFilter()
    fun clearAllFilters()
    fun getFilterParams(): VacancyFilter
}
