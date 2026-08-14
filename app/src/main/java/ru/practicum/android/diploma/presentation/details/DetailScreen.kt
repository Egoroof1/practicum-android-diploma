package ru.practicum.android.diploma.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.components.AppBarIcon
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.LightGray
import ru.practicum.android.diploma.ui.theme.WhiteUniversal
import ru.practicum.android.diploma.util.createTextSalary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    itemId: String,
    navController: NavController,
    viewModel: DetailViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.getVacancyById(itemId)
    val vacancy = state.vacancy

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTheme {
                AppTopBar(
                    title = stringResource(id = R.string.title_main),
                    onBackClick = { navController.navigateUp() },
                    actions = {
                        AppBarIcon(
                            iconRes = R.drawable.ic_sharing_24px,
                            contentDescription = stringResource(id = R.string.description_filter),
                            onClick = {},
                        )
                        AppBarIcon(
                            iconRes = R.drawable.ic_favorites_off_24px,
                            contentDescription = stringResource(id = R.string.favorites),
                            onClick = {},
                        )
                    },
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(vacancy?.name ?: "0000")
            Text(createTextSalary(vacancy?.salaryFrom, vacancy?.salaryTo, vacancy?.salaryCurrency))
            Text(vacancy?.logo ?: "---")

            Row(
                modifier = Modifier
                    .background(
                        color = LightGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxWidth()
                    .height(80.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = vacancy?.logo,
                    contentDescription = "Логотип компании",
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(WhiteUniversal, RoundedCornerShape(8.dp))
                )
            }
        }
    }
}
