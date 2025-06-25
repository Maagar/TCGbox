package data.Presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import data.Presentation.component.CardList
import data.Presentation.component.CardSearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCardsScreen() {
    val query by remember { mutableStateOf(("")) }

    Column {
        CardSearchBar(query, onQueryChange = {})
        CardList()
    }

}