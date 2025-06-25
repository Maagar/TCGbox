package data.Presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import data.model.Card
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import util.openURL

@Composable
fun CardListItem(card: Card) {
    ListItem(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        headlineContent = {
            Text(text = card.name)
        },
        leadingContent = {
            KamelImage({ asyncPainterResource(data = card.imageSmall) },
                contentDescription = null,
                modifier = Modifier.size(72.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit,
                alpha = DefaultAlpha,
                contentAlignment = Alignment.Center
            )
        },
        trailingContent = {
            Text(text = "${card.marketPrice}")
        },
        supportingContent = {
            Text(
                text = card.tcgPlayerUrl,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.clickable(onClick = { openURL(card.tcgPlayerUrl) })
                    .pointerHoverIcon(PointerIcon.Hand)
            )
        }
    )
}