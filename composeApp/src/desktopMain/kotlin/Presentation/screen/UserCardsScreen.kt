package Presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import Presentation.component.CardList
import Presentation.component.CardSearchBar
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCardsScreen() {
    val userCardsViewModel: UserCardsViewModel = koinInject()
    val query by remember { mutableStateOf(("")) }

    val pokemonCard by userCardsViewModel.pokemonCard.collectAsState()
    val isLoading by userCardsViewModel.isLoading.collectAsState()
    val error by userCardsViewModel.error.collectAsState()

    Column {
        CardSearchBar(query, onQueryChange = {})
        CardList()
        Button(onClick = {
            userCardsViewModel.fetchPokemonCard("xy1-1")
        }) {
            Text("fetch")
        }
        pokemonCard?.let { card ->
            Column {
                Text("Nazwa karty: ${card.name}")
                Text("ID karty: ${card.id}")
            }
        }
    }

}