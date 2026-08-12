package ru.practicum.android.diploma.data.dto

data class VacancyFullResponseDto(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val schedule: ScheduleDto?,
    val employment: EmploymentDto?,
    val contacts: ContactsDto?,
    val description: String?,
    val employer: EmployerDto?,
    val area: AreaDto?,
    val skills: List<String>?,
    val url: String?,
    val industry: IndustryDto?
)

data class AddressDto(
    val id: String?,
    val city: String?,
    val street: String?,
    val building: String?,
    val raw: String?
)

data class ExperienceDto(
    val id: String?,
    val name: String?
)

data class ScheduleDto(
    val id: String?,
    val name: String?
)

data class EmploymentDto(
    val id: String?,
    val name: String?
)

data class ContactsDto(
    val id: String?,
    val name: String?,
    val email: String?,
    val phones: List<PhoneDto>?
)

data class PhoneDto(
    val comment: String?,
    val formatted: String?
)

data class EmployerDto(
    val id: String?,
    val name: String?,
    val logo: String?
)

data class AreaDto(
    val id: String?,
    val parentId: String?,
    val name: String?,
    val areas: List<AreaDto>? = emptyList()
)

data class IndustryDto(
    val id: String?,
    val name: String?
)
