package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.domain.db.VacancyDbInteractor
import ru.practicum.android.diploma.domain.db.impl.VacancyDbInteractorImpl

val interactorModule = module {

    factory<VacancyDbInteractor> { VacancyDbInteractorImpl(get()) }
}
