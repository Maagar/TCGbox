package Presentation.screen.userCards.component

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import com.tcgbox.database.Cards
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.more_vert
import util.GetCustomScrollbarStyle

@Composable
fun CardList(pokemonCards: List<Cards>) {

    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxHeight()) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState
        ) {
            items(pokemonCards) { card ->
                LocalCardListItem(card, painterResource(Res.drawable.more_vert), {})

                HorizontalDivider()
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight().pointerHoverIcon(PointerIcon.Hand),
            adapter = rememberScrollbarAdapter(listState),
            style = GetCustomScrollbarStyle()
        )
    }

}