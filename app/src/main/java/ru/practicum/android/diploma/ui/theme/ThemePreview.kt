package ru.practicum.android.diploma.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ThemePreview() {
    AppTheme {
        Surface {
            Column {
                Text(text = "Основной текст экрана")
                Text(text = "Сбросить", color = MaterialTheme.colorScheme.error)
                Button(onClick = {}) {
                    Text(text = "Откликнуться")
                }
            }
        }
    }
}
