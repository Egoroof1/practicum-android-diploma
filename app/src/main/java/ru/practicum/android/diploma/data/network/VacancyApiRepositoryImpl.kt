package ru.practicum.android.diploma.data.network

import retrofit2.HttpException
import ru.practicum.android.diploma.data.mapper.VacancyApiConverter
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.domain.network.VacancyApiRepository
import ru.practicum.android.diploma.util.Resource
import java.net.ConnectException
import java.net.UnknownHostException

class VacancyApiRepositoryImpl(
    private val vacancyApi: VacancyApi,
    private val converter: VacancyApiConverter
) : VacancyApiRepository {

    override suspend fun searchAllVacancies(
        text: String?,
        page: Int?
    ): Resource<List<VacancyShort>> {
        return try {
            val response = vacancyApi.searchAllVacancies(text, page)
            if (response.items.isNotEmpty()) {
                Resource.Success(
                    data = response.items.map { converter.mapToVacancyShort(it) },
                    totalFound = response.found,
                    totalPages = response.pages,
                    currentPage = response.page
                )
            } else {
                Resource.Empty()
            }
        } catch (e: ConnectException) {
            Resource.Error("Не удалось подключиться к серверу. Проверьте интернет-соединение. ${e.message}")
        } catch (e: UnknownHostException) {
            Resource.Error("Отсутствует подключение к интернету. Проверьте настройки сети. ${e.message}")
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> Resource.Error("Неверный запрос. Проверьте введенные данные.")
                401 -> Resource.Error("Ошибка авторизации. Попробуйте войти снова.")
                403 -> Resource.Error("Доступ запрещен. У вас недостаточно прав.")
                404 -> Resource.Error("Ресурс не найден.")
                500 -> Resource.Error("Ошибка на сервере. Попробуйте позже.")
                503 -> Resource.Error("Сервер временно недоступен. Попробуйте позже.")
                else -> Resource.Error("Ошибка сервера: ${e.code()}")
            }
        } catch (e: Exception) {
            Resource.Error("Произошла неизвестная ошибка: ${e.message}")
        }
    }

    override suspend fun getVacancyById(vacancyId: String): Resource<VacancyFull> {
        return try {
            val response = vacancyApi.getVacancyById(vacancyId)
            Resource.Success(data = converter.mapToVacancyFull(response))
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ошибка при загрузке вакансии")
        }
    }
}
