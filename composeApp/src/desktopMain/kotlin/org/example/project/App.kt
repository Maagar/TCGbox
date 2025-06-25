package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import data.Presentation.screen.UserCardsScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val apiKey: String = System.getProperty("API_KEY") ?: "default_key"

        UserCardsScreen()
    }
}
