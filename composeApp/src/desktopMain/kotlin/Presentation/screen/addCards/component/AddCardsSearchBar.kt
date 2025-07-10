package Presentation.screen.addCards.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import org.jetbrains.compose.resources.painterResource
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.refresh
import tcgbox.composeapp.generated.resources.search
import tcgbox.composeapp.generated.resources.tag
import tcgbox.composeapp.generated.resources.view_carousel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddCardsSearchBar(
    pokemonSets: List<com.tcgbox.database.Sets>,
    onSearchTriggered: (String, String, String, String) -> Unit,
    refreshSets: () -> Unit,
) {
    var name by rememberSaveable() { mutableStateOf("") }
    var number by rememberSaveable() { mutableStateOf("") }
    var setQuery by rememberSaveable { mutableStateOf("") }
    var selectedSetId by rememberSaveable { mutableStateOf("") }
    var showSuggestions by rememberSaveable() { mutableStateOf(false) }

    // Stan przechowujący szerokość pola tekstowego dodatku
    var setTextFieldWidth by rememberSaveable { mutableStateOf(0) }
    val density = LocalDensity.current

    val filteredSets = pokemonSets.filter {
        it.name.contains(setQuery, ignoreCase = true)
    }

    fun triggerSearch() {
        onSearchTriggered(name, number, selectedSetId, if (selectedSetId.isEmpty()) setQuery else "")
    }

    val textFieldHeight = 56.dp
    val topPadding = 8.dp

    Row(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            placeholder = { Text(text = "Wyszukaj kartę...") },
            singleLine = true,
            modifier = Modifier.
            padding(top = 8.dp)
                .height(56.dp)
                .weight(2f)
                .fillMaxWidth()
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        if (name.isNotEmpty()) {
                            triggerSearch()
                        }
                        true
                    } else {
                        false
                    }
                },
            leadingIcon = {
                Icon(painterResource(Res.drawable.search), contentDescription = null)
            })

        VerticalDivider(modifier = Modifier.padding(top = topPadding).height(textFieldHeight))

        TextField(
            value = number,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() } && newValue.length <= 3) {
                    number = newValue
                }
            },
            placeholder = { Text(text = "Numer karty...") },
            singleLine = true,
            modifier = Modifier.padding(top = topPadding).height(textFieldHeight).weight(1f).fillMaxWidth().onKeyEvent {
                if (it.key == Key.Enter) {
                    if (number.isNotEmpty()) {
                        triggerSearch()
                    }
                    true
                } else {
                    false
                }
            },
            leadingIcon = {
                Icon(painterResource(Res.drawable.tag), contentDescription = null)
            })

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
                        if (it.key == Key.Enter) {
                            if (setQuery.isNotEmpty()) {
                                triggerSearch()
                            }
                            showSuggestions = false
                            true
                        } else if (it.key == Key.Escape) {
                            showSuggestions = false
                            true
                        } else {
                            false
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
                Popup(
                    alignment = Alignment.TopStart,
                    offset = IntOffset(0, with(density) { textFieldHeight.roundToPx() }), // Użyj wysokości pola tekstowego jako offsetu
                    properties = PopupProperties(focusable = false)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .width(with(density) { setTextFieldWidth.toDp() }) // Użyj zmierzonej szerokości
                            .heightIn(max = 200.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        items(filteredSets) { setSuggestion ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        setQuery = setSuggestion.name
                                        selectedSetId = setSuggestion.id
                                        showSuggestions = false
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(setSuggestion.name)
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}