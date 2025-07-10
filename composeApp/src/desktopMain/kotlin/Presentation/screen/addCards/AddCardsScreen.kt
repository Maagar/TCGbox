package Presentation.screen.addCards

import Presentation.component.CardListItem
import Presentation.screen.addCards.component.AddCardPopUp
import Presentation.screen.addCards.component.AddCardsSearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import data.api.model.ApiCard
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.add_box

@Composable
fun AddCardsScreen() {
    val addCardsViewModel: AddCardsViewModel = koinInject()
    val pokemonCards by addCardsViewModel.pokemonCards.collectAsState()
    val pokemonSets by addCardsViewModel.pokemonSets.collectAsState()
    var selectedCard: ApiCard? by remember { mutableStateOf(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column {
        AddCardsSearchBar(
            pokemonSets,
            { name, number, setId, setNumber ->
                addCardsViewModel.fetchPokemonCards(name, number, setId, setNumber)
            },
            {
                addCardsViewModel.refreshPokemonSets()
            })
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            items(pokemonCards) { card ->
                Row {
                    CardListItem(card, painterResource(Res.drawable.add_box), onIconClick = {
                        showDialog = true
                        selectedCard = card
                    })
                }

                HorizontalDivider()
            }
        }
    }
    if (showDialog && selectedCard != null) {
        AddCardPopUp(selectedCard!!, onDismiss = { showDialog = false }, onAddCard = { cards, onResult ->
            addCardsViewModel.insertCard(cards) { success ->
                onResult(success)
            }
        })
    }


}