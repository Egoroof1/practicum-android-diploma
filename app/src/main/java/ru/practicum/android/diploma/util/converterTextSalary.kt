package ru.practicum.android.diploma.util

fun createTextSalary(from: Int?, to: Int?, currency: String?): String {
    val fromPart = if (from != null) "От $from" else ""
    val toPart = if (to != null) "До $to" else ""

    val currency = when (currency?.uppercase()) {
        "RUR", "RUB" -> "₽"
        "BYR" -> "Br"
        "USD" -> "$"
        "EUR" -> "€"
        "KZT" -> "₸"
        "UAH" -> "₴"
        "AZN" -> "₼"
        "UZS" -> "soʻm"
        "GEL" -> "₾"
        "KGT" -> "сом"
        else -> ""
        }
    return when {
        from != null && to != null -> "$fromPart $toPart $currency".trim()
        from != null -> "$fromPart $currency".trim()
        to != null -> "$toPart $currency".trim()
        else -> "Зарплата не указана"
    }
}
