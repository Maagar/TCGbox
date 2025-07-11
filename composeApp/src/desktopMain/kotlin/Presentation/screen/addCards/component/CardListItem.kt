package Presentation.screen.addCards.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import data.api.model.ApiCard
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import util.openURL

@Composable
fun CardListItem(card: ApiCard, icon: Painter, onIconClick: () -> Unit, boughtPrice: Long? = null) {

    var showLargeImage by remember { mutableStateOf(false) }

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

    ListItem(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        headlineContent = {
            Text(text = card.name, style = MaterialTheme.typography.titleLarge)
        },
        leadingContent = {
            KamelImage(
                { asyncPainterResource(data = card.images.small) },
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (card.cardmarket != null) {
                    Text("${card.cardmarket.prices.trendPrice / 100.0} €", style = MaterialTheme.typography.bodyLarge)
                    if (boughtPrice != null) {
                        val priceDifference = card.cardmarket.prices.trendPrice - boughtPrice
                        val sign = when {
                            priceDifference > 0 -> "+"
                            else -> ""
                        }
                        Text(
                            text = " ($sign${priceDifference / 100.0})",
                            style = MaterialTheme.typography.bodyLarge,
                            color = when {
                                priceDifference > 0 -> Color.Green
                                priceDifference < 0 -> Color.Red
                                else -> MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                } else {
                    Text("N/A", style = MaterialTheme.typography.bodyLarge)
                }
                IconButton(onClick = onIconClick) {
                    Icon(icon, modifier = Modifier.size(28.dp), contentDescription = null)
                }
            }


        },
        supportingContent = {
            card.cardmarket?.let {
                Text(
                    text = it.url,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable(onClick = { openURL(card.cardmarket.url) })
                        .pointerHoverIcon(PointerIcon.Hand)
                )
            }
        }
    )
}