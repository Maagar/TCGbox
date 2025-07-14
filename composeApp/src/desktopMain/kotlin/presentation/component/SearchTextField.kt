package presentation.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.close

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: Painter,
    modifier: Modifier = Modifier,
    liveSearchEnabled: Boolean,
    onSearchTriggered: () -> Unit,
    isNumberField: Boolean = false
) {
    val textFieldHeight = 56.dp
    val topPadding = 8.dp

    TextField(
        value = value,
        onValueChange = { newValue ->
            if (isNumberField) {
                if (newValue.length <= 4 || newValue.isEmpty()) {
                    onValueChange(newValue)
                    if (liveSearchEnabled) onSearchTriggered()
                }
            } else {
                onValueChange(newValue)
                if (liveSearchEnabled) onSearchTriggered()
            }
        },
        placeholder = { Text(text = placeholder) },
        singleLine = true,
        modifier = modifier
            .padding(top = topPadding)
            .height(textFieldHeight)
            .onKeyEvent {
                if (!liveSearchEnabled && it.key == Key.Enter) {
                    onSearchTriggered()
                    true
                } else false
            },
        leadingIcon = {
            Icon(leadingIcon, contentDescription = null)
        },
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(modifier = Modifier.pointerHoverIcon(PointerIcon.Hand), onClick = { onValueChange("") }) {
                    Icon(painterResource(Res.drawable.close), contentDescription = null)
                }
            }
        }

    )
}