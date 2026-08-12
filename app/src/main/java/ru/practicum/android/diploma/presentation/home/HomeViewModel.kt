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

    fun searchVacancies(query: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null,
                vacancies = emptyList()
            )

            val result = searchVacanciesUseCase(
                text = query.takeIf { it.isNotBlank() },
                page = 0
            )

            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        vacancies = result.data,
                        errorMessage = if (result.data.isEmpty()) "Ничего не найдено" else null
                    )
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
}

data class HomeState(
    val isLoading: Boolean = false,
    val vacancies: List<VacancyShort> = emptyList(),
    val errorMessage: String? = null,
    val searchQuery: String = ""
)
