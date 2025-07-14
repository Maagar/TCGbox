package presentation.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun refreshPokemonSets() {
        viewModelScope.launch {
            _isLoading.value = true
            pokemonRepository.getPokemonSets()
                .onFailure { exception ->
                    _error.value = "An error occurred: $exception"
                }
            _isLoading.value = false
        }
    }
}