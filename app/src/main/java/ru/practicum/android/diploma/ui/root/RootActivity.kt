package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.navigation.NavGraph

class RootActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)
        enableEdgeToEdge()

        setContent {
            NavGraph()
        }
    }

}
