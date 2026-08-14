package ru.practicum.android.diploma.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import java.util.Locale

private val currencySymbols = mapOf(
    "RUR" to "₽",
    "RUB" to "₽",
    "BYR" to "Br",
    "USD" to "$",
    "EUR" to "€",
    "KZT" to "₸",
    "UAH" to "₴",
    "AZN" to "₼",
    "UZS" to "сум",
    "GEL" to "₾",
    "KGT" to "сом"
)

@Composable
fun salaryText(from: Int?, to: Int?, currency: String?): String {
    if (from == null && to == null) {
        return stringResource(id = R.string.salary_not_specified)
    }
    val parts = listOfNotNull(
        from?.let { stringResource(id = R.string.salary_from, formatAmount(it)) },
        to?.let { stringResource(id = R.string.salary_to, formatAmount(it)) },
        currencySymbol(currency),
    )
    return parts.joinToString(separator = " ")
}

private fun currencySymbol(currency: String?): String? {
    val code = currency?.uppercase(Locale.ROOT) ?: return null
    return currencySymbols[code] ?: code
}

private fun formatAmount(amount: Int): String =
    String.format(Locale.ROOT, "%,d", amount).replace(oldChar = ',', newChar = ' ')
