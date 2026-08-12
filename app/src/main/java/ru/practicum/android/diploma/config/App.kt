package ru.practicum.android.diploma.config

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.useCaseModule
import ru.practicum.android.diploma.di.viewModelModule
import ru.practicum.android.diploma.util.NetworkManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        NetworkManager.init(this)

        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                repositoryModule,
                interactorModule,
                viewModelModule,
                useCaseModule
            )
        }
    }
}
