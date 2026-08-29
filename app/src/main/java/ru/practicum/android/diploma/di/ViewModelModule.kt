package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.details.DetailViewModel
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filter.FilterViewModel
import ru.practicum.android.diploma.presentation.home.HomeViewModel

val viewModelModule = module {
    viewModel {
        HomeViewModel(
            searchVacanciesUseCase = get()
        )
    }

    viewModel {
        DetailViewModel(
            getVacancyByIdUseCase = get(),
            vacancyDbInteractor = get()
        )
    }

    viewModel {
        FavoritesViewModel(
            vacancyDbInteractor = get()
        )
    }
    viewModel {
        FilterViewModel(
            getIndustriesUseCase = get(),
            filterInteractor = get()
        )
    }
}
