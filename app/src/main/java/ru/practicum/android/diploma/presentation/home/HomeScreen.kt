package ru.practicum.android.diploma.presentation.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.domain.models.VacancyShort
import ru.practicum.android.diploma.presentation.navigation.Screen
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var searchText by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Введите запрос") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.searchAllVacancies(
                    query = searchText
                )
            }
        ) {
            Text("Поиск вакансий")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Индикатор загрузки
        if (state.isLoading) {
            CircularProgressIndicator()
            Text("Загрузка...")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Количество найденных вакансий
        Text("Найдено вакансий: ${state.allVacanciesQuery}")

        //Item
        VacanciesList(
            state.vacancies,
            onItemClick = { vacancyShort ->
                navController.navigate(Screen.Detail.passId(vacancyShort.id))
            },
            onScrolledToEnd = {
                Log.i("TAG", "HomeScreen: skrolUp")
                viewModel.searchPlusPage()
            })

        Spacer(modifier = Modifier.height(24.dp))


        Button(
            onClick = {
                navController.navigate("detail/456")
            }
        ) {
            Text("Детали (ID: 456)")
        }
    }
}

@OptIn(FlowPreview::class)
@Composable
fun VacanciesList(
    vacancies: List<VacancyShort>,
    onItemClick: (VacancyShort) -> Unit,
    onScrolledToEnd: () -> Unit
) {
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .debounce(300.milliseconds)
            .collect { layoutInfo ->
                val totalItemsCount = layoutInfo.totalItemsCount
                val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0

                // Если последний видимый элемент - это последний элемент списка
                if (totalItemsCount > 0 && lastVisibleItemIndex >= totalItemsCount - 5) {
                    onScrolledToEnd()
                }
            }
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = vacancies,
            key = { it.id } // ← оптимизация, как RecyclerView с stable ids
        ) { vacancy ->
            VacancyItem(
                vacancy = vacancy,
                onClick = { onItemClick(vacancy) }
            )
        }
    }
}

@Composable
fun VacancyItem(
    vacancy: VacancyShort,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = vacancy.name,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = vacancy.company ?: "Компания не указана",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = vacancy.city,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

