package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun ResultCountChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimary,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.CornerRadius))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = Dimens.Spacing12, vertical = Dimens.Spacing4),
    )
}

@Preview(name = "Плашка: найдено", showBackground = true)
@Composable
private fun ResultCountChipFoundPreview() {
    AppTheme {
        ResultCountChip(
            text = pluralStringResource(
                id = R.plurals.vacancies_found,
                count = PREVIEW_COUNT,
                PREVIEW_COUNT,
            ),
            modifier = Modifier.padding(Dimens.Spacing16),
        )
    }
}

@Preview(name = "Плашка: не найдено", showBackground = true)
@Composable
private fun ResultCountChipEmptyPreview() {
    AppTheme {
        ResultCountChip(
            text = stringResource(id = R.string.vacancies_not_found),
            modifier = Modifier.padding(Dimens.Spacing16),
        )
    }
}

private const val PREVIEW_COUNT = 286
