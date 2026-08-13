package ru.practicum.android.diploma.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.network.usecase.SearchVacanciesUseCase
import ru.practicum.android.diploma.util.Resource

class HomeViewModel(
    private val searchVacanciesUseCase: SearchVacanciesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    var firstQuery: String = ""
    var page: Int = 1
    private var isLastPage: Boolean = false

    fun searchAllVacancies(query: String) {
        firstQuery = query
        page = 1
        isLastPage = false
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null,
                vacancies = emptyList()
            )

            val result = searchVacanciesUseCase.invoke(
                text = query,
                page = page
            )

            when (result) {
                is Resource.Empty -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        allVacanciesQuery = 0
                    )
                }

                is Resource.Success -> {
                    val data = result.data

                    _state.value = _state.value.copy(
                        isLoading = false,
                        vacancies = data,
                        errorMessage = if (data.isEmpty()) "Ничего не найдено" else null,
                        allVacanciesQuery = result.totalPages
                    )
                    // Если данных меньше чем ожидалось, значит это последняя страница
                    if (data.size < 20) { // или ваш лимит
                        isLastPage = true
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        vacancies = emptyList(),
                        errorMessage = result.message
                    )
                }

                else -> {}
            }
        }
    }

    fun searchPlusPage() {
        // Проверяем, что можно загружать следующую страницу
        if (_state.value.isLoading || isLastPage) {
            return
        }

        viewModelScope.launch {

            val nextPage = page + 1
            val result = searchVacanciesUseCase.invoke(
                text = firstQuery,
                page = nextPage
            )

            when (result) {
                is Resource.Success -> {
                    val newVacancies = result.data
                    val currentVacancies = _state.value.vacancies

                    val updatedVacancies = if (newVacancies.isNotEmpty()) {
                        currentVacancies + newVacancies
                    } else {
                        currentVacancies
                    }

                    _state.value = _state.value.copy(
                        vacancies = updatedVacancies,
                        errorMessage = if (updatedVacancies.isEmpty()) "Ничего не найдено" else null
                    )

                    // обновляем страницу только если есть новые данные
                    if (newVacancies.isNotEmpty()) {
                        page = nextPage
                        if (newVacancies.size < 20) {
                            isLastPage = true
                        }
                    } else {
                        isLastPage = true
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        errorMessage = result.message
                    )
                }

                else -> {}
            }
        }
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val vacancies: List<VacancyShort> = emptyList(),
    val allVacanciesQuery: Int? = null,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)
