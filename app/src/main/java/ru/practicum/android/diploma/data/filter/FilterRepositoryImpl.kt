package ru.practicum.android.diploma.data.filter

import com.google.gson.Gson
import ru.practicum.android.diploma.data.storage.SharedPreferencesStorage
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.Industry

class FilterRepositoryImpl(private val storage: SharedPreferencesStorage, val gson: Gson) : FilterRepository {
    override fun setIndustryFilter(industry: Industry) {
        val json = gson.toJson(industry)
        storage.setString(FilterKeys.INDUSTRY, json)
    }

    override fun getIndustryFilter(): Industry? {
        val json = storage.getString(FilterKeys.INDUSTRY) ?: return null
        return try {
            gson.fromJson(json, Industry::class.java)
        } catch (e: Exception) {
            null
        }
    }

    override fun removeIndustryFilter() {
        storage.removeFilter(FilterKeys.INDUSTRY)
    }

    override fun setWithSalaryFilter(isEnable: Boolean) {
        storage.setBoolean(FilterKeys.WITH_SALARY, isEnable)
    }

    override fun getWithSalaryFilter(): Boolean {
        return storage.getBoolean(FilterKeys.WITH_SALARY)
    }

    override fun removeWithSalaryFilter() {
        storage.removeFilter(FilterKeys.WITH_SALARY)
    }

    override fun setMinSalaryFilter(salary: Int) {
        storage.setInt(FilterKeys.MIN_SALARY, salary)
    }

    override fun getMinSalaryFilter(): Int {
        return storage.getInt(FilterKeys.MIN_SALARY)
    }

    override fun removeMinSalaryFilter() {
        storage.removeFilter(FilterKeys.MIN_SALARY)
    }

    override fun clearAllFilters() {
        storage.clearAll()
    }

}

object FilterKeys {
    const val INDUSTRY = "filter_industry"
    const val CITY = "filter_city"
    const val REGION = "filter_region"
    const val MIN_SALARY = "filter_min_salary"
    const val WITH_SALARY = "filter_salary"
}
