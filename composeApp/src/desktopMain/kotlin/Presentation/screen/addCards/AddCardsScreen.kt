package Presentation.screen.addCards

import Presentation.component.CardListItem
import Presentation.screen.addCards.component.AddCardsSearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

@Composable
fun AddCardsScreen() {
    val addCardsViewModel: AddCardsViewModel = koinInject()
    val pokemonCards by addCardsViewModel.pokemonCards.collectAsState()
    Column {
        AddCardsSearchBar({ query ->
            addCardsViewModel.fetchPokemonCards(query)
        })
        LazyColumn {
            items(pokemonCards) {card ->
                CardListItem(card)
            }
        }
    }

}