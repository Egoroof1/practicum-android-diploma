package ru.practicum.android.diploma.domain.filter

import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.VacancyFilter

interface FilterRepository {
    fun setIndustryFilter(industry: Industry?)
    fun removeIndustryFilter()
    fun setWithSalaryFilter(isEnable: Boolean)
    fun removeWithSalaryFilter()
    fun setMinSalaryFilter(salary: String)
    fun removeMinSalaryFilter()
    fun clearAllFilters()
    fun getFilterParams(): VacancyFilter
}
