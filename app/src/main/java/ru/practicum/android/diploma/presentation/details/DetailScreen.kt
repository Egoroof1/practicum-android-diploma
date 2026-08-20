package ru.practicum.android.diploma.presentation.details

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.models.VacancyFull
import ru.practicum.android.diploma.ui.components.AppBarIcon
import ru.practicum.android.diploma.ui.components.AppTopBar
import ru.practicum.android.diploma.ui.components.ScreenPlaceholder
import ru.practicum.android.diploma.ui.components.salaryText
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.ui.theme.BlackUniversal
import ru.practicum.android.diploma.ui.theme.Blue
import ru.practicum.android.diploma.ui.theme.Dimens
import ru.practicum.android.diploma.ui.theme.LightGray
import ru.practicum.android.diploma.ui.theme.WhiteUniversal
import ru.practicum.android.diploma.util.openDialerPhone
import ru.practicum.android.diploma.util.openEmail
import ru.practicum.android.diploma.util.shareVacancy

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(itemId: String, navController: NavController, viewModel: DetailViewModel = koinViewModel()) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = itemId) { viewModel.getVacancyById(itemId) }
    val vacancy = state.vacancy
    val salary: String = salaryText(vacancy?.salaryFrom, vacancy?.salaryTo, vacancy?.salaryCurrency)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTheme {
                AppTopBar(
                    title = stringResource(id = R.string.vacancy),
                    onBackClick = { navController.navigateUp() },
                    actions = {
                        if (state.vacancy != null) {
                            AppBarIcon(
                                iconRes = R.drawable.ic_sharing_24px,
                                contentDescription = stringResource(R.string.sharing),
                                onClick = { shareVacancy(context, vacancy, salary) },
                            )
                            AppBarIcon(
                                iconRes = if (state.isFavorite) {
                                    R.drawable.ic_favorites_on_24px
                                } else {
                                    R.drawable.ic_favorites_off_24px
                                },
                                contentDescription = stringResource(id = R.string.favorites),
                                onClick = { viewModel.toggleFavorite() }
                            )
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(Dimens.ProgressSize),
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        } else {
            if (!state.isConnected && state.vacancy == null) {
                ScreenPlaceholder(
                    imageRes = R.drawable.il_no_internet,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    text = stringResource(R.string.placeholder_no_internet),
                )
                viewModel.retryLoad(itemId)
            } else {
                if (vacancy != null) {
                    DetailContent(context, vacancy, innerPadding)
                } else {
                    ScreenPlaceholder(
                        imageRes = R.drawable.il_not_found_vacancy,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        text = stringResource(id = R.string.not_found_vacancy),
                    )
                }
            }
        }
    }
}

@Composable
private fun BtnSimilarVacancies() {
    Button(
        modifier = Modifier
            .padding(horizontal = 17.dp)
            .padding(top = 24.dp)
            .fillMaxWidth()
            .height(59.dp),
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(
            text = stringResource(R.string.similar_vacancies),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Default
        )
    }
}

@Composable
private fun DetailContent(context: Context, vacancy: VacancyFull, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        VacancyNameSalary(vacancy)
        LogoCompanyAddress(vacancy)
        ExperienceSchedule(vacancy)
        Text(
            text = stringResource(R.string.job_description),
            modifier = Modifier.padding(top = 32.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Default
        )

        VacancyListDetails(vacancy.description.responsibilities, stringResource(R.string.responsibilities))
        VacancyListDetails(vacancy.description.requirements, stringResource(R.string.requirements))
        VacancyListDetails(vacancy.description.conditions, stringResource(R.string.conditions))

        if (vacancy.skills.isNotEmpty()) {
            Text(
                text = "Ключевые навыки",
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Default
            )

            VacancyListDetails(vacancy.skills, "")
        }

        ContactInfo(context, vacancy)
        BtnSimilarVacancies()
    }
}

@Composable
fun ResponsibilitiesList(responsibilities: List<String>) {
    responsibilities.forEach { item ->
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "•",
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = item,
                fontSize = 16.sp,
                fontFamily = FontFamily.Default,
            )
        }
    }
}

@Composable
private fun VacancyNameSalary(vacancy: VacancyFull) {
    Text(
        text = vacancy.vacancyName,
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Default
    )
    Text(
        text = salaryText(vacancy.salaryFrom, vacancy.salaryTo, vacancy.salaryCurrency),
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    )
}

@Composable
private fun LogoCompanyAddress(vacancy: VacancyFull) {
    Row(
        modifier = Modifier
            .padding(vertical = 24.dp)
            .background(color = LightGray, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = vacancy.logo,
            contentDescription = stringResource(R.string.company_logo),
            placeholder = painterResource(R.drawable.ic_placeholder),
            modifier = Modifier
                .padding(start = 16.dp)
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(WhiteUniversal, RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(
                text = vacancy.company ?: "",
                fontSize = 22.sp,
                color = BlackUniversal,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Default
            )
            Text(
                text = vacancy.address ?: vacancy.city ?: "",
                fontSize = 16.sp,
                color = BlackUniversal,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Default
            )
        }
    }
}

@Composable
private fun ExperienceSchedule(vacancy: VacancyFull) {
    Text(
        text = stringResource(R.string.required_experience),
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    )
    Text(
        text = vacancy.experience ?: "",
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    )
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = vacancy.schedule ?: "",
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        fontFamily = FontFamily.Default
    )
}

@Composable
private fun ContactInfo(context: Context, vacancy: VacancyFull) {
    Text(
        text = stringResource(R.string.contacts),
        modifier = Modifier.padding(top = 20.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        fontFamily = FontFamily.Default
    )
    if (vacancy.name?.isNotEmpty() == true) {
        Text(
            text = stringResource(R.string.contact_person),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )

        Text(
            text = vacancy.name,
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
        )
    }

    if (!vacancy.email.isNullOrEmpty()) {
        Text(
            text = stringResource(R.string.email),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )
        Text(
            text = vacancy.email,
            fontSize = 16.sp,
            fontFamily = FontFamily.Default,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { openEmail(context, vacancy.email) }
        )
    }

    if (vacancy.phone.isNotEmpty()) {
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = pluralStringResource(id = R.plurals.phone_label, count = vacancy.phone.size),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.Default
        )
        vacancy.phone.forEach { (number, comment) ->
            Column(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    text = number,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.Default,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { openDialerPhone(context, number) }
                )
                if (comment.isNotEmpty()) {
                    Text(
                        text = comment,
                        fontSize = 14.sp,
                        fontFamily = FontFamily.Default,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }
}

@Composable
fun VacancyListDetails(array: List<String>?, nameList: String) {
    if (nameList.isNotEmpty()) {
        Text(
            text = nameList,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )
    }
    ResponsibilitiesList(responsibilities = array ?: emptyList())
}
