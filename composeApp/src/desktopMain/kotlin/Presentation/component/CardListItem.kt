package Presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import data.model.Card
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import util.openURL

@Composable
fun CardListItem(card: Card) {

    var showLargeImage by remember { mutableStateOf(false) }

    if (showLargeImage) {
        Popup(onDismissRequest = { showLargeImage = false }, properties = PopupProperties(focusable = true)) {
            KamelImage(
                { asyncPainterResource(data = card.imageLarge) },
                contentDescription = null,
                modifier = Modifier.padding(40.dp).clickable { showLargeImage = false },
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                alpha = DefaultAlpha,
                contentAlignment = Alignment.Center
            )
        }
    }

    ListItem(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        headlineContent = {
            Text(text = card.name)
        },
        leadingContent = {
            KamelImage(
                { asyncPainterResource(data = card.imageSmall) },
                contentDescription = null,
                modifier = Modifier.size(72.dp).clickable(onClick = { showLargeImage = true })
                    .pointerHoverIcon(PointerIcon.Hand),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                alpha = DefaultAlpha,
                contentAlignment = Alignment.Center
            )
        },
        trailingContent = {

            val priceDifference = card.marketPrice - card.boughtPrice
            val sign = when {
                priceDifference > 0 -> "+"
                else -> ""
            }
            Row {
                Text("$${card.marketPrice / 100.0}")
                Text(
                    text = " ($sign${priceDifference / 100.0})",
                    color = when {
                        priceDifference > 0 -> Color.Green
                        priceDifference < 0 -> Color.Red
                        else -> Color.Black
                    }
                )
            }

        },
        supportingContent = {
            Text(
                text = card.tcgPlayerUrl,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable(onClick = { openURL(card.tcgPlayerUrl) })
                    .pointerHoverIcon(PointerIcon.Hand)
            )
        }
    )
}