package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.AppTheme
import ru.practicum.android.diploma.presentation.navigation.NavGraph
import ru.practicum.android.diploma.util.NetworkManager

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        enableEdgeToEdge()

        checkInternetOnStart()

        lifecycleScope.launch {
            NetworkManager.getConnectionFlow().collect { isConnected ->
                Log.i("NetworkCheck", if (isConnected) "The internet is connected" else "The internet is disconnected")

                // Тут можно будет обновлять UI или какой нибудь Toast
            }
        }

        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppContent()
                }
            }
        }
    }

    private fun checkInternetOnStart() {
        val isConnected = NetworkManager.isConnected()
        if (isConnected) {
            Log.d("NetworkCheck", "The internet is connected")
        } else {
            Log.d("NetworkCheck", "The internet is disconnected")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        NetworkManager.unregister()
    }

    @Composable
    private fun AppContent() {
        Box(modifier = Modifier.fillMaxSize()) {
            NavGraph()
//            InternetStatus() Ещё мольца доработать может пригодится
        }
    }
}
