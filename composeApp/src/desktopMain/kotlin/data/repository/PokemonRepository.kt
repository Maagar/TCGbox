package data.repository

import com.tcgbox.database.AppDatabase
import data.api.PokemonApiService
import data.api.model.ApiCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepository(
    private val pokemonApiService: PokemonApiService,
    private val appDatabase: AppDatabase
) {

    suspend fun getPokemonCardDetails(cardId: String): Result<ApiCard> {
        return runCatching {
            pokemonApiService.getPokemonCard(cardId).data
        }
    }

    suspend fun getPokemonCards(name: String, number: String, setId: String, setName: String): Result<List<ApiCard>> {
        return runCatching {
            pokemonApiService.getPokemonCards(name, number, setId, setName).data
        }
    }

    suspend fun getPokemonSets(): Result<Unit> {
        return runCatching {
            val apiResponse = pokemonApiService.getPokemonSets().data

            withContext(Dispatchers.IO) {
                appDatabase.setsQueries.transaction{
                    apiResponse.forEach { pokemonSet ->
                        appDatabase.setsQueries.insertSet(
                            id = pokemonSet.id,
                            name = pokemonSet.name,
                            printedTotal = pokemonSet.printedTotal.toLong()
                        )
                        println(pokemonSet)

                    }
                }
                Unit
            }
        }
    }

    suspend fun getLocalPokemonSets(): Result<List<com.tcgbox.database.Sets>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                appDatabase.setsQueries.getAllSets().executeAsList()
            }
        }
    }
}