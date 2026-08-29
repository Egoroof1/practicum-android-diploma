package ru.practicum.android.diploma.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.network.usecase.SearchVacanciesUseCase
import ru.practicum.android.diploma.presentation.filter.FilterState
import ru.practicum.android.diploma.util.NetworkManager
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

private const val SEARCH_DEBOUNCE_DELAY = 2000L
private const val FIRST_PAGE = 1

class HomeViewModel(
    private val searchVacanciesUseCase: SearchVacanciesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private var firstQuery: String = ""
    private var page: Int = FIRST_PAGE
    private var isLastPage: Boolean = false
    private var searchJob: Job? = null
    private var nextPageJob: Job? = null

    private val searchDebounced = debounce<String>(
        delayMillis = SEARCH_DEBOUNCE_DELAY,
        coroutineScope = viewModelScope,
        useLastParam = true
    ) { query ->
        val trimmed = query.trim()
        val alreadyShown = trimmed == firstQuery && _state.value.vacancies.isNotEmpty()
        if (trimmed.isNotEmpty() && !alreadyShown) {
            searchAllVacancies(trimmed)
        }
    }

    fun onQueryChange(query: String) {
        if (_state.value.searchQuery == query) return
        searchDebounced(query)
        if (query.isBlank()) {
            cancelRequests()
            _state.value = HomeState(searchQuery = query)
        } else {
            updateState { it.copy(searchQuery = query) }
        }
    }

    fun onSearchClick() {
        val trimmed = _state.value.searchQuery.trim()
        if (trimmed.isNotEmpty()) {
            searchAllVacancies(trimmed)
        }
    }

    private fun searchAllVacancies(query: String) {
        firstQuery = query
        page = FIRST_PAGE
        isLastPage = false

        cancelRequests()
        searchJob = viewModelScope.launch {
            updateState { it.copy(isLoading = true, error = null, vacancies = emptyList()) }
            handleFirstPage(searchVacanciesUseCase.invoke(text = query, page = page))
        }
    }

    private fun handleFirstPage(result: Resource<List<VacancyShort>>) {
        when (result) {
            is Resource.Success -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        vacancies = result.data,
                        allVacanciesQuery = result.totalFound
                    )
                }
                isLastPage = result.currentPage >= result.totalPages
            }

            is Resource.Empty -> {
                updateState { it.copy(isLoading = false, allVacanciesQuery = 0, error = SearchError.LoadFailed) }
                isLastPage = true
            }

            is Resource.Error -> {
                updateState { it.copy(isLoading = false, vacancies = emptyList(), error = currentError()) }
            }

            is Resource.Loading -> Unit
        }
    }

    fun searchPlusPage() {
        val current = _state.value
        if (current.isLoading || current.isNextPageLoading || isLastPage) {
            return
        }
        nextPageJob = viewModelScope.launch {
            updateState { it.copy(isNextPageLoading = true) }
            val nextPage = page + 1
            handleNextPage(nextPage, searchVacanciesUseCase.invoke(text = firstQuery, page = nextPage))
        }
    }

    private fun cancelRequests() {
        searchJob?.cancel()
        nextPageJob?.cancel()
    }

    private fun handleNextPage(nextPage: Int, result: Resource<List<VacancyShort>>) {
        updateState { it.copy(isNextPageLoading = false) }
        when (result) {
            is Resource.Success -> {
                updateState {
                    it.copy(
                        vacancies = (it.vacancies + result.data).distinctBy { vacancy -> vacancy.id },
                        error = null
                    )
                }
                page = nextPage
                isLastPage = result.currentPage >= result.totalPages
            }

            is Resource.Empty -> isLastPage = true

            is Resource.Error -> updateState { it.copy(error = currentError()) }

            is Resource.Loading -> Unit
        }
    }

    private fun currentError(): SearchError =
        if (NetworkManager.isConnected()) SearchError.LoadFailed else SearchError.NoInternet

    private fun updateState(updater: (HomeState) -> HomeState) {
        val currentState = _state.value
        _state.value = updater(currentState)
    }

    fun updateFilterParamsSate(filterState: FilterState?) {
        updateState { it.copy(newFilterParam = filterState) }
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val isNextPageLoading: Boolean = false,
    val vacancies: List<VacancyShort> = emptyList(),
    val allVacanciesQuery: Int? = null,
    val error: SearchError? = null,
    val searchQuery: String = "",
    val newFilterParam: FilterState? = null
)

enum class SearchError {
    NoInternet,
    LoadFailed
}
