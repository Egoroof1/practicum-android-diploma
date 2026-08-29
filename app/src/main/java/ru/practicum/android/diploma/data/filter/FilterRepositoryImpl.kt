package ru.practicum.android.diploma.data.filter

import com.google.gson.Gson
import ru.practicum.android.diploma.data.storage.SharedPreferencesStorage
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.VacancyFilter

class FilterRepositoryImpl(private val storage: SharedPreferencesStorage, val gson: Gson) : FilterRepository {
    override fun setVacancyFilter(vacancyFilter: VacancyFilter) {
        val json = gson.toJson(vacancyFilter)
        storage.setString(FilterKeys.VACANCY_FILTER, json)
    }

    override fun clearVacancyFilter() {
        storage.clearAll()
    }

    override fun getVacancyFilter(): VacancyFilter {
        val json = storage.getString(FilterKeys.VACANCY_FILTER) ?: return VacancyFilter(null, null, null)
        return try {
            gson.fromJson(json, VacancyFilter::class.java)
        } catch (e: Exception) {
            e.stackTrace
            VacancyFilter(null, null, null)
        }
    }
}

object FilterKeys {
    const val VACANCY_FILTER = "vacancy_filter"
}
