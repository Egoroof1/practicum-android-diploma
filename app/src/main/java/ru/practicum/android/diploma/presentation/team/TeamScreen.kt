package ru.practicum.android.diploma.presentation.team

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.Developer
import ru.practicum.android.diploma.ui.components.AppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamScreen() {
    val developers = listOf(
        Developer(
            name = "Дмитрий Егоров",
            role = "Team Lead",
            imageRes = R.drawable.img_egorov,
            github = "https://github.com/Egoroof1"
        ),
        Developer(
            name = "Данила Веригин",
            role = "Android Developer",
            imageRes = R.drawable.img_verigin,
            iconRes = R.drawable.ic_dev_48,
            github = "https://github.com/IPaCm4nI"
        ),
        Developer(
            name = "Степан Логинов",
            role = "Android Developer",
            imageRes = R.drawable.img_loginov,
            iconRes = R.drawable.ic_dev_48,
            github = "https://github.com/I-Am-Morald"
        )
    )

    val context = LocalContext.current
    var selectedDev by remember { mutableStateOf<Developer?>(null) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(topBar = { AppTopBar(title = stringResource(R.string.team)) }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp)
        )
        {
            item {
                Text("Над приложением работали:", style = MaterialTheme.typography.titleLarge)
            }
            items(items = developers, key = { it.name }) { dev ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically { it / 2 },
                ) {
                    TeamItem(dev, onClick = { selectedDev = dev })
                }
            }
        }
    }

    selectedDev?.let { developer ->
        ModalBottomSheet(
            onDismissRequest = { selectedDev = null },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            DeveloperBottomSheetContent(
                developer = developer,
                onGithubClick = { openLink(context, it) }
            )
        }
    }
}


@Preview(name = "Portrait")
@Composable
private fun TeamPreview() {
    TeamScreen()
}

