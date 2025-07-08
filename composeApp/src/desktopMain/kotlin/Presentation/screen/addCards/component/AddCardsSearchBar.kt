package Presentation.screen.addCards.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.SearchBar
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.search

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddCardsSearchBar(
    onSearchTriggered: (String) -> Unit,
) {
    var query by rememberSaveable() { mutableStateOf("") }
    var active by rememberSaveable() { mutableStateOf(false) }
    SearchBar(
        modifier = Modifier.padding(16.dp),
        inputField = {
            TextField(
                value = query,
                onValueChange = {
                    query = it
                    if (it.isNotEmpty() && !active) {

                    } else if (it.isEmpty() && active) {
                        active = false
                    }
                },
                placeholder = { Text(text = "Wyszukaj kartę...") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().onKeyEvent {
                    if (it.key == Key.Enter) {
                        if (query.isNotEmpty()) {
                            onSearchTriggered(query)
                            active = false
                        }
                        true
                    } else {
                        false
                    }
                },
                leadingIcon = {
                    Icon(painterResource(Res.drawable.search), contentDescription = null)
                })
        },
        expanded = active,
        onExpandedChange = { active = it }
    ) {

    }
}