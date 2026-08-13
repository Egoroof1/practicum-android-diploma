package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.db.domain.VacancyInteractor
import ru.practicum.android.diploma.db.domain.impl.VacancyInteractorImpl

val interactorModule = module {

    factory<VacancyInteractor> { VacancyInteractorImpl(get()) }
}
