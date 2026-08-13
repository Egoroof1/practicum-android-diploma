package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.details.DetailViewModel
import ru.practicum.android.diploma.presentation.home.HomeViewModel

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            searchVacanciesUseCase = get()
        )
    }

    viewModel {
        DetailViewModel(
            getVacancyByIdUseCase = get()
        )
    }
}
