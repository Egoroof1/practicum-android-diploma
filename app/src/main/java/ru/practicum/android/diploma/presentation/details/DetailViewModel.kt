package ru.practicum.android.diploma.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.db.VacancyDbInteractor
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.network.usecase.GetVacancyByIdUseCase
import ru.practicum.android.diploma.util.NetworkManager
import ru.practicum.android.diploma.util.Resource

class DetailViewModel(
    private val getVacancyByIdUseCase: GetVacancyByIdUseCase,
    private val vacancyDbInteractor: VacancyDbInteractor
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
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val newState = getVacancyState(vacancyId)
            _state.value = newState
        }
    }

    private suspend fun getVacancyState(vacancyId: String): DetailState {
        val current = _state.value
        return when {
            vacancyDbInteractor.isVacancyInFavorites(vacancyId) -> {
                val localVacancy = vacancyDbInteractor.getVacancyById(vacancyId)
                current.copy(isFavorite = true, vacancy = localVacancy, isLoading = false)
            }

            !current.isConnected -> {
                current.copy(isLoading = false, errorMessage = "Нет интернета")
            }

            else -> when (val result = getVacancyByIdUseCase(vacancyId = vacancyId)) {
                is Resource.Success -> {
                    current.copy(
                        isLoading = false,
                        vacancy = result.data
                    )
                }

                is Resource.Error -> {
                    current.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                else -> {
                    current.copy(
                        isLoading = false,
                        errorMessage = "Произошла неизвестная ошибка"
                    )
                }
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
        val currentVacancy = _state.value.vacancy ?: return
        val currentState = _state.value.isFavorite
        viewModelScope.launch {
            try {
                if (currentState) {
                    vacancyDbInteractor.removeVacancyById(currentVacancy.id)
                } else {
                    vacancyDbInteractor.insertVacancy(currentVacancy)
                }
                _state.value = _state.value.copy(isFavorite = !currentState)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = "Не удалось изменить статус избранного"
                )
                e.stackTrace
            }
        }
    }
}

data class DetailState(
    val isFavorite: Boolean = false,
    val vacancy: VacancyFull? = null,
    val isLoading: Boolean = false,
    val isConnected: Boolean = false,
    val errorMessage: String? = null
)
