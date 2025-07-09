package Presentation.screen.addCards.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import data.api.model.ApiCard
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardPopUp(card: ApiCard, onDismiss: () -> Unit) {
    var showLargeImage by remember { mutableStateOf(false) }

    BasicAlertDialog(onDismissRequest = onDismiss, Modifier, DialogProperties()) {
        Surface {
            Row(modifier = Modifier.padding(16.dp)) {
                KamelImage(
                    { asyncPainterResource(data = card.images.large) },
                    contentDescription = null,
                    modifier = Modifier.size(300.dp).clickable(onClick = { showLargeImage = true })
                        .pointerHoverIcon(PointerIcon.Hand),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                    alpha = DefaultAlpha,
                    contentAlignment = Alignment.Center
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text("Nazwa: ${card.name}", style = MaterialTheme.typography.headlineSmall)
                    Text("Dodatek: ${card.set.name}")
                    Text("Numer: ${card.number}/${card.set.printedTotal}")


                }
            }

        }
    }

    if (showLargeImage) {
        Popup(onDismissRequest = { showLargeImage = false }, properties = PopupProperties(focusable = true)) {
            KamelImage(
                { asyncPainterResource(data = card.images.large) },
                contentDescription = null,
                modifier = Modifier.padding(40.dp).clickable { showLargeImage = false },
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                alpha = DefaultAlpha,
                contentAlignment = Alignment.Center
            )
        }
    }
}