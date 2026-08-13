package ru.practicum.android.diploma.util

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InternetStatus() {
    val isConnected by NetworkManager.getConnectionFlow().collectAsState(initial = true)

    if (!isConnected) {
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = {
                    Log.i("NetworkCheck", "Типо обновили")
                }) {
                    Text("Обновить")
                }
            }
        ) {
            Text("Отсутствует подключение к интернету")
        }
    }
}
