package ru.practicum.android.diploma.domain.filter

import ru.practicum.android.diploma.domain.models.Industry

interface FilterRepository {
    fun setIndustryFilter(industry: Industry)
    fun getIndustryFilter(): Industry?
    fun removeIndustryFilter()
    fun setWithSalaryFilter(isEnable: Boolean)
    fun getWithSalaryFilter(): Boolean
    fun removeWithSalaryFilter()
    fun setMinSalaryFilter(salary: Int)
    fun getMinSalaryFilter(): Int
    fun removeMinSalaryFilter()
    fun clearAllFilters()
}
