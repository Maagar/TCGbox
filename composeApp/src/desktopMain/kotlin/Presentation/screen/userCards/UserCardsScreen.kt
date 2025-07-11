package Presentation.screen.userCards

import Presentation.component.CardSearchBar
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import Presentation.screen.userCards.component.CardList
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCardsScreen() {
    val userCardsViewModel: UserCardsViewModel = koinInject()
    val pokemonCards by userCardsViewModel.localPokemonCards.collectAsState()

    val pokemonSets by userCardsViewModel.pokemonSets.collectAsState()
    val pokemonCard by userCardsViewModel.pokemonCard.collectAsState()
    val isLoading by userCardsViewModel.isLoading.collectAsState()
    val error by userCardsViewModel.error.collectAsState()

    var filterName by remember { mutableStateOf("") }
    var filterNumber by remember { mutableStateOf("") }
    var filterSetId by remember { mutableStateOf("") }
    var filterSetName by remember { mutableStateOf("") }

    val filteredCards = remember (
        pokemonCards,
        filterName,
        filterNumber,
        filterSetId,
        filterSetName
    ) {
        pokemonCards.filter { card ->
            val matchesName = filterName.isEmpty() || card.name.contains(filterName, ignoreCase = true)
            val matchesNumber = filterNumber.isEmpty() || card.number.equals(filterNumber)

            val matchesSet = if (filterSetId.isNotEmpty()) {
                card.setId == filterSetId
            } else {
                filterSetName.isEmpty() || card.setName.contains(filterSetName, ignoreCase = true)
            }

            matchesName && matchesNumber && matchesSet
        }
    }

    Column {
        CardSearchBar(pokemonSets, { name, number, setId, setName ->
            filterName = name
            filterNumber = number
            filterSetId = setId
            filterSetName = setName
        }, {
            userCardsViewModel.refreshPokemonSets()
        }, liveSearchEnabled = true)

        CardList(filteredCards)
        pokemonCard?.let { card ->
            Column {
                Text("Nazwa karty: ${card.name}")
                Text("ID karty: ${card.id}")
            }
        }
    }

}