package Presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import data.model.Card
import kotlinx.datetime.LocalDate

@Composable
fun CardList() {

    val verticalScroll = rememberScrollState(0)

    val card = Card(
        id = "xy1",
        name = "Venusaur-EX",
        releaseDate = LocalDate(2014, 2, 5),
        addedDate = LocalDate(2014, 2, 5),
        imageSmall = "https://images.pokemontcg.io/xy1/1.png",
        imageLarge = "https://images.pokemontcg.io/xy1/1_hires.png",
        tcgPlayerUrl = "https://prices.pokemontcg.io/tcgplayer/xy1-1",
        marketPrice = 332L,
        boughtPrice = 400L,
    )

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            CardListItem(card)
        }
    }
}