package ru.practicum.android.diploma.presentation.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.domain.network.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.util.Resource

class FilterViewModel(
    private val getIndustriesUseCase: GetIndustriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FilterState())
    val state: StateFlow<FilterState> = _state.asStateFlow()

    init {
        loadIndustries()
    }

    fun loadIndustries() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            when (val result = getIndustriesUseCase.invoke()) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        listIndustries = result.data,
                        errorMessage = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }

                else -> {
                    // Resource.Loading
                }
            }
        }
    }
}

data class FilterState(
    val listIndustries: List<Industries> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
