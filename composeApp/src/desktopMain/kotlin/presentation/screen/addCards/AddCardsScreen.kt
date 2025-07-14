package presentation.screen.addCards

import presentation.screen.addCards.component.CardListItem
import presentation.screen.addCards.component.AddCardPopUp
import presentation.component.CardSearchBar
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import data.api.model.ApiCard
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import tcgbox.composeapp.generated.resources.Res
import tcgbox.composeapp.generated.resources.add_box
import util.GetCustomScrollbarStyle

@Composable
fun AddCardsScreen() {
    val addCardsViewModel: AddCardsViewModel = koinInject()

    val isLoading by addCardsViewModel.isLoading.collectAsState()
    val pokemonCards by addCardsViewModel.pokemonCards.collectAsState()
    val pokemonSets by addCardsViewModel.pokemonSets.collectAsState()
    val canLoadMore by addCardsViewModel.canLoadMore.collectAsState()

    var selectedCard: ApiCard? by remember { mutableStateOf(null) }
    var showDialog by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()

    LaunchedEffect(listState, isLoading, canLoadMore) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .filter { lastVisibleItemIndex ->
                if (lastVisibleItemIndex == null) return@filter false
                val totalItemsCount = listState.layoutInfo.totalItemsCount
                (!isLoading && canLoadMore && (lastVisibleItemIndex >= totalItemsCount - 10) && totalItemsCount > 0)
            }
            .collect {
                addCardsViewModel.loadNextPage()
            }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CardSearchBar(
            pokemonSets,
            { name, number, setId, setName ->
                addCardsViewModel.searchAndFetchCards(name, number, setId, setName)
            },
            {
                addCardsViewModel.refreshPokemonSets()
            },
            showSearchButton = true)
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxHeight(), state = listState) {
                items(pokemonCards) { card ->
                    CardListItem(card, painterResource(Res.drawable.add_box), onIconClick = {
                        showDialog = true
                        selectedCard = card
                    })

                    HorizontalDivider()
                }
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }

            VerticalScrollbar(
                modifier = Modifier.padding(4.dp).align(Alignment.CenterEnd).fillMaxHeight().pointerHoverIcon(PointerIcon.Hand),
                adapter = rememberScrollbarAdapter(listState),
                style = GetCustomScrollbarStyle()
            )
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