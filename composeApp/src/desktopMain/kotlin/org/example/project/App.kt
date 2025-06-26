package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import Presentation.screen.UserCardsScreen
import androidx.compose.foundation.isSystemInDarkTheme
import com.example.compose.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
) {
    AppTheme(darkTheme = darkTheme, dynamicColor = dynamicColor) {
        Surface(modifier = Modifier.fillMaxSize()) {
            UserCardsScreen()
        }
    }

}
