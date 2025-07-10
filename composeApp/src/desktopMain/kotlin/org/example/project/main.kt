package org.example.project

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initializeKoin
import org.koin.core.context.stopKoin
import java.awt.Dimension

fun main() = application {
    initializeKoin()

    Window(
        onCloseRequest = {
            stopKoin()
            exitApplication()
        },
        title = "TCGbox",

    ) {
        window.minimumSize = Dimension(900, 700)
        App(
            darkTheme = isSystemInDarkTheme(),
            dynamicColor = false
        )
    }
}