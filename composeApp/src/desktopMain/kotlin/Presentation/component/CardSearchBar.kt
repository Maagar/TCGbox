package Presentation.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.tcgbox.database.Sets
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.refresh
import tcgbox.composeapp.generated.resources.search
import tcgbox.composeapp.generated.resources.tag
import tcgbox.composeapp.generated.resources.view_carousel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CardSearchBar(
    pokemonSets: List<Sets>,
    onSearchTriggered: (String, String, String, String) -> Unit,
    refreshSets: () -> Unit,
    liveSearchEnabled: Boolean = false,
) {
    var name by rememberSaveable() { mutableStateOf("") }
    var number by rememberSaveable() { mutableStateOf("") }
    var setQuery by rememberSaveable { mutableStateOf("") }
    var selectedSetId by rememberSaveable { mutableStateOf("") }
    var showSuggestions by rememberSaveable() { mutableStateOf(false) }

    var setTextFieldWidth by rememberSaveable { mutableStateOf(0) }

    val filteredSets = pokemonSets.filter {
        it.name.contains(setQuery, ignoreCase = true)
    }

    fun triggerSearch() {
        onSearchTriggered(name, number, selectedSetId, setQuery)
    }

    val textFieldHeight = 56.dp
    val topPadding = 8.dp

    Row(modifier = Modifier.padding(16.dp)) {
        SearchTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = "Wyszukaj kartę...",
            leadingIcon = painterResource(Res.drawable.search),
            liveSearchEnabled = liveSearchEnabled,
            onSearchTriggered = { triggerSearch() },
            modifier = Modifier.weight(2f),
        )

        VerticalDivider(modifier = Modifier.padding(top = topPadding).height(textFieldHeight))

        SearchTextField(
            value = number,
            onValueChange = { number = it },
            placeholder = "Numer karty...",
            leadingIcon = painterResource(Res.drawable.tag),
            liveSearchEnabled = liveSearchEnabled,
            onSearchTriggered = { triggerSearch() },
            isNumberField = true,
            modifier = Modifier.weight(1f),
        )

        VerticalDivider(modifier = Modifier.padding(top = topPadding).height(textFieldHeight))

        Box(modifier = Modifier.padding(top = topPadding).weight(1f)) {
            TextField(
                value = setQuery,
                onValueChange = {
                    setQuery = it
                    showSuggestions = it.isNotEmpty() && !pokemonSets.any { set -> set.name == it }
                    if (selectedSetId.isNotEmpty() && !pokemonSets.any { set -> set.name == it }) {
                        selectedSetId = ""
                    }
                    if (liveSearchEnabled) triggerSearch()
                },
                placeholder = { Text(text = "Dodatek...") },
                singleLine = true,
                modifier = Modifier
                    .height(textFieldHeight)
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        setTextFieldWidth = coordinates.size.width
                    }
                    .onFocusChanged { focusState ->
                        showSuggestions =
                            focusState.isFocused && setQuery.isNotEmpty() && !pokemonSets.any { set ->
                                set.name.equals(
                                    setQuery,
                                    ignoreCase = true
                                )
                            }
                    }
                    .onKeyEvent {
                        when (it.key) {
                            Key.Enter -> {
                                if (!liveSearchEnabled) triggerSearch()
                                showSuggestions = false
                                true
                            }

                            Key.Escape -> {
                                showSuggestions = false
                                true
                            }

                            else -> false
                        }
                    },
                leadingIcon = {
                    Icon(painterResource(Res.drawable.view_carousel), contentDescription = null)
                },
                trailingIcon = {
                    Icon(
                        painterResource(Res.drawable.refresh),
                        modifier = Modifier.padding(4.dp).pointerHoverIcon(PointerIcon.Hand).clickable {
                            refreshSets()
                        },
                        contentDescription = null
                    )
                })
            if (showSuggestions && filteredSets.isNotEmpty()) {
                SetSuggestionsPopup(
                    filteredSets = filteredSets,
                    setTextFieldWidth = setTextFieldWidth,
                    textFieldHeight = textFieldHeight,
                    onSetSelected = { setSuggestion ->
                        setQuery = setSuggestion.name
                        selectedSetId = setSuggestion.id
                        showSuggestions = false
                        if (liveSearchEnabled) triggerSearch()
                    },
                    onDismiss = { showSuggestions = false }
                )
            }
        }
    }
}