package Presentation.screen.addCards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tcgbox.database.Cards
import data.api.model.ApiCard
import data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddCardsViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _pokemonCards = MutableStateFlow<List<ApiCard>>(emptyList())
    val pokemonCards: StateFlow<List<ApiCard>> = _pokemonCards

    private val _pokemonSets = MutableStateFlow<List<com.tcgbox.database.Sets>>(emptyList())
    val pokemonSets: StateFlow<List<com.tcgbox.database.Sets>> = _pokemonSets

    init {
        fetchPokemonSets()
    }

    fun fetchPokemonCards(name: String, number: String, setId: String, setName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.getPokemonCards(name, number, setId, setName)
                .onSuccess { pokemonCards ->
                    _pokemonCards.value = pokemonCards
                }
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

    fun insertCard(cards: Cards, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.insertPokemonCard(cards)
                .onSuccess { onResult(true) }
                .onFailure { exception ->
                    println(exception)
                    _error.value = "An error occurred: $exception"
                    onResult(false) }
            _isLoading.value = false
        }
    }
}