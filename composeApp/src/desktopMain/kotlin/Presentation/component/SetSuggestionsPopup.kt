package Presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.tcgbox.database.Sets

@Composable
fun SetSuggestionsPopup(
    filteredSets: List<Sets>,
    setTextFieldWidth: Int,
    textFieldHeight: Dp,
    onSetSelected: (Sets) -> Unit,
    onDismiss: () -> Unit
) {
    val density = LocalDensity.current

    Popup(
        alignment = androidx.compose.ui.Alignment.TopStart,
        offset = IntOffset(0, with(density) { textFieldHeight.roundToPx() }),
        properties = PopupProperties(focusable = false),
        onDismissRequest = onDismiss
    ) {
        LazyColumn(
            modifier = Modifier
                .width(with(density) { setTextFieldWidth.toDp() })
                .heightIn(max = 200.dp)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outline)
        ) {
            items(filteredSets) { setSuggestion ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSetSelected(setSuggestion)
                        }
                        .padding(8.dp)
                ) {
                    Text(setSuggestion.name)
                }
                HorizontalDivider()
            }
        }
    }
}