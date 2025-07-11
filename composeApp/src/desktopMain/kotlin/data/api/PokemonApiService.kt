package data.api

import data.api.model.PokemonCardApiResponse
import data.api.model.PokemonSetsApiResponse
import data.api.model.SearchCardsApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class PokemonApiService(private val client: HttpClient) {

    private val baseUrl = "https://api.pokemontcg.io/v2"
    val apiKey: String = System.getProperty("API_KEY") ?: "default_key"

    suspend fun getPokemonCard(cardId: String): PokemonCardApiResponse {
        return client.get("$baseUrl/cards/$cardId/?select=id,name,images,set,cardmarket") {
            header("X-Api-Key", apiKey)
        }.body()
    }

    suspend fun getPokemonCards(
        name: String,
        number: String,
        setId: String,
        setName: String,
        page: Int
    ): SearchCardsApiResponse {
        var query = ""
        if (name.isNotEmpty()) query += "name:\"$name\" "
        if (number.isNotEmpty()) query += "number:\"$number\" "
        if (setId.isNotEmpty()) query += "set.id:\"$setId\" " else if (setId.isEmpty() && setName.isNotEmpty()) query += "set.name:\"$setName\" "
        println("query: $query")

        return client.get("$baseUrl/cards") {
            parameter("page", page)
            parameter("pageSize", 50)

            parameter("q", query)

            parameter("select", "id,name,images,set,cardmarket,number")

            header("X-Api-Key", apiKey)
            header("X-Api-Key", apiKey)
        }.body()
    }

    suspend fun getPokemonSets(): PokemonSetsApiResponse {
        return client.get("$baseUrl/sets") {
            parameter("select", "id,name,printedTotal")
            header("X-Api-Key", apiKey)
            header("X-Api-Key", apiKey)
        }.body()
    }
}