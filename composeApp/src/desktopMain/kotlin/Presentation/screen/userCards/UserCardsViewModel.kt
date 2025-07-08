package Presentation.screen.userCards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.api.model.ApiCard
import data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserCardsViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _pokemonCard = MutableStateFlow<ApiCard?>(null)
    val pokemonCard: StateFlow<ApiCard?> = _pokemonCard

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

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