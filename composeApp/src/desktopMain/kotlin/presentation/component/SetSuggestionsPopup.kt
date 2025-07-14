package presentation.component

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.tcgbox.database.Sets
import util.GetCustomScrollbarStyle

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
        onDismissRequest = onDismiss,
    ) {
        val listState = rememberLazyListState()

        Row(modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outline).background(MaterialTheme.colorScheme.surfaceVariant)) {
            LazyColumn(
                modifier = Modifier
                    .width(with(density) { setTextFieldWidth.toDp() - 8.dp })
                    .heightIn(max = 200.dp),
                state = listState
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
            VerticalScrollbar(
                modifier = Modifier.heightIn(max = 200.dp).pointerHoverIcon(PointerIcon.Hand),
                adapter = rememberScrollbarAdapter(listState),
                style = GetCustomScrollbarStyle()
            )
        }

    }
}