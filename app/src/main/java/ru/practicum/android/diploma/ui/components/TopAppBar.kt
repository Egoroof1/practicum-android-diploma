package ru.practicum.android.diploma.ui.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(text = title)
        },
        navigationIcon = {
            if (onBackClick != null) {
                AppBarIcon(
                    iconRes = R.drawable.ic_arrow_back_24px,
                    contentDescription = stringResource(id = R.string.description_back),
                    onClick = onBackClick,
                )
            }
        },
        actions = {
            Row(modifier = Modifier.padding(end = Dimens.Spacing4)) {
                actions()
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
            actionIconContentColor = MaterialTheme.colorScheme.onBackground,
        ),
    )
}

@Composable
fun AppBarIcon(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onBackground,
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            painterResource(id = iconRes),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Preview(name = "Только заголовок")
@Preview(name = "Только заголовок (тёмная)", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTopBarTitleOnlyPreview() {
    AppTheme {
        AppTopBar(title = stringResource(id = R.string.title_main))
    }
}

@Preview(name = "Заголовок и фильтр")
@Composable
private fun AppTopBarWithFilterPreview() {
    AppTheme {
        AppTopBar(
            title = stringResource(id = R.string.title_main),
            actions = {
                AppBarIcon(
                    iconRes = R.drawable.ic_filter_off_24px,
                    contentDescription = stringResource(id = R.string.description_filter),
                    onClick = {},
                )
            },
        )
    }
}
