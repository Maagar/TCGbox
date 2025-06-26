package org.example.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initializeKoin
import org.koin.core.context.stopKoin

fun main() = application {
    initializeKoin()

    Window(
        onCloseRequest = {
            stopKoin()
            exitApplication()
        },
        title = "TCGbox",
    ) {
        App(
            darkTheme = isSystemInDarkTheme(),
            dynamicColor = false
        )
    }
}