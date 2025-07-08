package Presentation.screen.addCards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.api.model.ApiCard
import data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddCardsViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonCards = MutableStateFlow<List<ApiCard>>(emptyList())
    val pokemonCards: StateFlow<List<ApiCard>> = _pokemonCards

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun fetchPokemonCards(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.getPokemonCards(query)
                .onSuccess { pokemonCards ->
                    _pokemonCards.value = pokemonCards
                    println(pokemonCards)
                }
                .onFailure { exception ->
                    _error.value = "An error occurred: $exception"
                    println("An error occurred: $exception")
                    exception.printStackTrace()
                }
            _isLoading.value = false
        }
    }
}