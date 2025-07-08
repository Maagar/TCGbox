package data.repository

import data.api.PokemonApiService
import data.api.model.ApiCard

class PokemonRepository(private val pokemonApiService: PokemonApiService) {

    suspend fun getPokemonCardDetails(cardId: String): Result<ApiCard> {
        return runCatching {
            pokemonApiService.getPokemonCard(cardId).data
        }
    }

    suspend fun getPokemonCards(query: String): Result<List<ApiCard>> {
        return runCatching {
            pokemonApiService.getPokemonCards(query).data
        }
    }
}