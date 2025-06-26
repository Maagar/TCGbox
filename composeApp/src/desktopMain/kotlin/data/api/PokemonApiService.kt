package data.api

import data.api.model.PokemonCardApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class PokemonApiService(private val client: HttpClient) {

    private val baseUrl = "https://api.pokemontcg.io/v2"
    val apiKey: String = System.getProperty("API_KEY") ?: "default_key"

    suspend fun getPokemonCard(cardId: String): PokemonCardApiResponse {
        return client.get("$baseUrl/cards/$cardId") {
            header("X-Api-Key", apiKey)
        }.body()
    }
}