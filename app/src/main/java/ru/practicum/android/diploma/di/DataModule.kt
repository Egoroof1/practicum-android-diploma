package ru.practicum.android.diploma.di

import androidx.room.Room
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.mapper.VacancyApiConverter
import ru.practicum.android.diploma.data.mapper.VacancyDbConverter
import ru.practicum.android.diploma.data.network.VacancyApi
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://android-diploma.education-services.ru"

val dataModule = module {

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val request = original.newBuilder()
                    .header("Authorization", "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
                    .header("User-Agent", "Android Diploma App")
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single<VacancyApi> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VacancyApi::class.java)
    }

    factory { Gson() }

    // === Database ===
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "vacancy_database.db"
        ).build()
    }

    // === Mappers ===
    single { VacancyDbConverter() }
    single { VacancyApiConverter() }
}
