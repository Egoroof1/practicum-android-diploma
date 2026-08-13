package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.network.usecase.GetVacancyByIdUseCase
import ru.practicum.android.diploma.util.Resource

class DetailViewModel(
    private val getVacancyByIdUseCase: GetVacancyByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    fun getVacancyById(vacancyId: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                errorMessage = null,
                vacancy = null
            )

            val result = getVacancyByIdUseCase(
                vacancyId = vacancyId
            )

            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        vacancy = result.data
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        vacancy = null,
                        errorMessage = result.message
                    )
                }

                else -> {}
            }
        }
    }
}

data class DetailState(
    val isFavorite: Boolean = false,
    val vacancy: VacancyFull? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
