package ru.practicum.android.diploma.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens

@Composable
fun ScreenPlaceholder(
    @DrawableRes imageRes: Int,
    modifier: Modifier = Modifier,
    text: String? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.Spacing16),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing16, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = stringResource(id = R.string.description_placeholder),
        )
        if (text != null) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview(name = "Заглушка без текста", showBackground = true)
@Composable
private fun ScreenPlaceholderNoTextPreview() {
    AppTheme {
        ScreenPlaceholder(imageRes = R.drawable.il_search_start)
    }
}

@Preview(name = "Заглушка с текстом", showBackground = true)
@Preview(
    name = "Заглушка с текстом (тёмная)",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun ScreenPlaceholderWithTextPreview() {
    AppTheme {
        ScreenPlaceholder(
            imageRes = R.drawable.il_no_internet,
            text = stringResource(id = R.string.placeholder_no_internet),
        )
    }
}
