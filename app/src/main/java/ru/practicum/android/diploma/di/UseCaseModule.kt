package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.network.usecase.GetVacancyByIdUseCase
import ru.practicum.android.diploma.domain.network.usecase.SearchVacanciesUseCase

val useCaseModule = module {

    factory { SearchVacanciesUseCase(get()) }
    factory { GetVacancyByIdUseCase(get()) }
}
