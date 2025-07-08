package data.api

import data.api.model.PokemonCardApiResponse
import data.api.model.SearchCardsApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header

class PokemonApiService(private val client: HttpClient) {

    private val baseUrl = "https://api.pokemontcg.io/v2"
    val apiKey: String = System.getProperty("API_KEY") ?: "default_key"

    suspend fun getPokemonCard(cardId: String): PokemonCardApiResponse {
        return client.get("$baseUrl/cards/$cardId/?select=id,name,images,set,cardmarket") {
            header("X-Api-Key", apiKey)
        }.body()
    }

    suspend fun getPokemonCards(query: String): SearchCardsApiResponse {
        return client.get("$baseUrl/cards?page=1&pageSize=20&q=name:$query&select=id,name,images,set,cardmarket") {
            header("X-Api-Key", apiKey)
        }.body()
    }
}