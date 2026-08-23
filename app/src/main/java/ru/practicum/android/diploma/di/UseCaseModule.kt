package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.network.usecase.GetIndustriesUseCase
import ru.practicum.android.diploma.domain.network.usecase.GetIndustriesUseCaseImpl
import ru.practicum.android.diploma.domain.network.usecase.GetVacancyByIdUseCase
import ru.practicum.android.diploma.domain.network.usecase.GetVacancyByIdUseCaseImpl
import ru.practicum.android.diploma.domain.network.usecase.SearchVacanciesUseCase
import ru.practicum.android.diploma.domain.network.usecase.SearchVacanciesUseCaseImpl

val useCaseModule = module {
    factory<SearchVacanciesUseCase> { SearchVacanciesUseCaseImpl(get()) }
    factory<GetVacancyByIdUseCase> { GetVacancyByIdUseCaseImpl(get()) }
    factory<GetIndustriesUseCase> { GetIndustriesUseCaseImpl(get()) }
}
