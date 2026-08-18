package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.network.usecase.GetVacancyByIdUseCase
import ru.practicum.android.diploma.util.NetworkManager
import ru.practicum.android.diploma.util.Resource

class DetailViewModel(
    private val getVacancyByIdUseCase: GetVacancyByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(DetailState())
    val state: StateFlow<DetailState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            NetworkManager.getConnectionFlow().collect { isConnected ->
                _state.value = _state.value.copy(isConnected = isConnected)
            }
        }
    }

    fun getVacancyById(vacancyId: String) {
        if (!_state.value.isConnected) {
            _state.value = _state.value.copy(
                isLoading = false, errorMessage = "Нет интернета"
            )
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            when (val result = getVacancyByIdUseCase(vacancyId = vacancyId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false, vacancy = result.data
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false, errorMessage = result.message
                    )
                }

                else -> {}
            }
        }
    }

    fun retryLoad(vacancyId: String) {
        // Повторная попытка загрузки
        if (_state.value.isConnected) {
            getVacancyById(vacancyId)
        }
    }

    fun toggleFavorite() {
        _state.value = _state.value.copy(
            isFavorite = !_state.value.isFavorite
        )
    }
}

data class DetailState(
    val isFavorite: Boolean = false,
    val vacancy: VacancyFull? = null,
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val shouldRetry: Boolean = false,
    val errorMessage: String? = null
)
