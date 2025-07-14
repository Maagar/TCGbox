package presentation.screen.userCards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcgbox.database.Cards
import com.tcgbox.database.Sets
import data.api.model.ApiCard
import data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserCardsViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _localPokemonCards = MutableStateFlow<List<Cards>>(emptyList())
    val localPokemonCards: StateFlow<List<Cards>> = _localPokemonCards

    private val _pokemonCard = MutableStateFlow<ApiCard?>(null)
    val pokemonCard: StateFlow<ApiCard?> = _pokemonCard

    private val _pokemonSets = MutableStateFlow<List<Sets>>(emptyList())
    val pokemonSets: StateFlow<List<Sets>> = _pokemonSets

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchLocalPokemonCards()
        fetchPokemonSets()
    }

    fun fetchLocalPokemonCards() {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.getAllLocalCards()
                .onSuccess { _localPokemonCards.value = it }
                .onFailure { exception ->
                    _error.value = "An error occurred: $exception"
                    println("An error occurred: $exception")
                    exception.printStackTrace()
                }
            _isLoading.value = false
        }
    }

    fun fetchPokemonSets() {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.getLocalPokemonSets()
                .onSuccess { sets ->
                    _pokemonSets.value = sets
                }.onFailure { exception ->
                    _error.value = "An error occurred: $exception"
                }
            _isLoading.value = false
        }
    }

    fun refreshPokemonSets() {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.getPokemonSets()
                .onSuccess { fetchPokemonSets() }
                .onFailure { exception ->
                    _error.value = "An error occurred: $exception"
                }
            _isLoading.value = false
        }
    }

    fun deletePokemonCard(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.deleteCard(id)
                .onSuccess { fetchLocalPokemonCards() }
                .onFailure { exception ->
                    _error.value = "An error occurred: $exception"
                }
        }
    }

    fun updatePokemonCard(card: Cards) {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.updateCard(
                date = card.addedDate,
                boughtPrice = card.boughtPriceCents,
                isReverseHolo = card.isReverseHolo,
                cardId = card.id
            ).onSuccess { fetchLocalPokemonCards() }
            .onFailure { exception ->
                _error.value = "An error occurred: $exception"
            }
            _isLoading.value = false
        }
    }

    fun fetchPokemonCard(cardId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            pokemonRepository.getPokemonCardDetails(cardId)
                .onSuccess { card ->
                    _pokemonCard.value = card
                }
                .onFailure {exception ->
                    _error.value = "An error occurred: $exception"
                    println("An error occurred: $exception")
                    exception.printStackTrace()
                }
            _isLoading.value = false
        }
    }
}