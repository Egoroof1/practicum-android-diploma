package ru.practicum.android.diploma.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.db.VacancyDbInteractor

class FavoritesViewModel(
    private val vacancyInteractor: VacancyDbInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<FavoritesState>(FavoritesState.Loading)
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            vacancyInteractor.getVacanciesList()
                .catch { _state.value = FavoritesState.Error }
                .collect { vacancies ->
                    _state.value = if (vacancies.isEmpty()) {
                        FavoritesState.Empty
                    } else {
                        FavoritesState.Content(vacancies)
                    }
                }
        }
    }
}
