package util

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.ScrollbarStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun GetCustomScrollbarStyle(): ScrollbarStyle {
    val customScrollbarStyle = ScrollbarStyle(
        minimalHeight = 32.dp,
        thickness = 8.dp,
        shape = LocalScrollbarStyle.current.shape,
        hoverColor = MaterialTheme.colorScheme.primary,
        unhoverColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
        hoverDurationMillis = LocalScrollbarStyle.current.hoverDurationMillis,
    )
    return customScrollbarStyle
}
