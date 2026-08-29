package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.VacancyDbInteractor
import ru.practicum.android.diploma.domain.db.impl.VacancyDbInteractorImpl
import ru.practicum.android.diploma.domain.filter.FilterInteractor
import ru.practicum.android.diploma.domain.filter.impl.FilterInteractorImpl

val interactorModule = module {

    factory<VacancyDbInteractor> { VacancyDbInteractorImpl(get()) }

    single<FilterInteractor> { FilterInteractorImpl(get()) }
}
