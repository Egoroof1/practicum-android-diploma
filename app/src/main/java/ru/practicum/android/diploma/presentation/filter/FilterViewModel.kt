package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filter.FilterRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.network.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.util.Resource
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class FilterViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase,
    private val filterRepository: FilterRepository
) : ViewModel() {
    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state.asStateFlow()

    private var allIndustries: List<Industry> = emptyList()
    private val searchQuery = MutableStateFlow("")

    init {
        loadIndustries()
        viewModelScope.launch {
            searchQuery.debounce(300.milliseconds).collect { query -> performFilter(query) }
        }
        resetToLastAppliedFilter()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = getIndustriesUseCase.invoke()) {
                is Resource.Success -> {
                    allIndustries = result.data
                    _state.value = _state.value.copy(
                        isLoading = false,
                        listIndustries = allIndustries,
                        errorMessage = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                else -> {}
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery.value = query.trim()
    }

    private fun performFilter(query: String) {
        val filtered = if (query.isEmpty()) {
            allIndustries
        } else {
            val queryLower = query.lowercase()

            allIndustries
                .map { industry ->
                    val nameLower = industry.name.lowercase()
                    val words = nameLower.split(Regex("[ ,.\\-()]")).filter { it.isNotBlank() }

                    // Вычисляем релевантность
                    val relevance = when {
                        nameLower == queryLower -> 100
                        nameLower.startsWith(queryLower) -> 90
                        words.any { it == queryLower } -> 80
                        words.any { it.startsWith(queryLower) } -> 70
                        nameLower.contains(" $queryLower") -> 60
                        nameLower.contains(queryLower) -> 40
                        else -> 0
                    }

                    industry to relevance
                }
                .filter { it.second > 0 }
                .sortedByDescending { it.second }
                .map { it.first }
        }

        _state.value = _state.value.copy(listIndustries = filtered)
    }

    fun setFilter() {
        val newOnlyWithSalary = _state.value.isOnlyWithSalary
        val newMinSalary = _state.value.selectedSalary
        val newIndustry = _state.value.selectedIndustry
        filterRepository.setWithSalaryFilter(newOnlyWithSalary)
        filterRepository.setMinSalaryFilter(newMinSalary)
        filterRepository.setIndustryFilter(newIndustry)
        _state.value = _state.value.copy(isFilterChanged = false)

    }

    fun isFilterCleared() {
        _state.value = _state.value.copy(isFilterChanged = false)
    }

    fun isOnlySalaryChanged(newValue: Boolean) {
        _state.value = _state.value.copy(isOnlyWithSalary = newValue, isFilterChanged = true)
    }

    fun onSalaryChange(salary: String) {
        _state.value = _state.value.copy(selectedSalary = salary, isFilterChanged = true)
    }

    fun selectIndustry(industry: Industry) {
        _state.value = _state.value.copy(selectedIndustry = industry, isFilterChanged = true)
    }

    fun clearIndustry() {
        _state.value = _state.value.copy(selectedIndustry = null, isFilterChanged = true)
    }

    fun resetToLastAppliedFilter() {
        val savedFilter = filterRepository.getFilterParams()
        _state.value = _state.value.copy(
            isOnlyWithSalary = savedFilter.onlyWithSalary ?: false,
            selectedSalary = savedFilter.minSalary ?: "", selectedIndustry = savedFilter.industry
        )
    }
}

data class FilterState(
    val listIndustries: List<Industry> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedIndustry: Industry? = null,
    val selectedSalary: String = "",
    val isOnlyWithSalary: Boolean = false,
    val isFilterChanged: Boolean = false
)
