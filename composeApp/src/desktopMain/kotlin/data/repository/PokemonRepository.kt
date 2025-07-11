package data.repository

import com.tcgbox.database.AppDatabase
import com.tcgbox.database.Cards
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

    suspend fun getPokemonCards(name: String, number: String, setId: String, setName: String, page: Int): Result<List<ApiCard>> {
        return runCatching {
            pokemonApiService.getPokemonCards(name, number, setId, setName, page).data
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

    suspend fun insertPokemonCard(cards: Cards): Result<Unit> {
        return runCatching {
            withContext(Dispatchers.IO) {
                appDatabase.cardsQueries.insertCard(
                    cardId = cards.cardId,
                    name = cards.name,
                    number = cards.number,
                    imageSmall = cards.imageSmall,
                    imageLarge = cards.imageLarge,
                    setId = cards.setId,
                    setName = cards.setName,
                    cardMarketUrl = cards.cardMarketUrl,
                    trendPriceCents = cards.trendPriceCents,
                    reverseHoloTrendCents = cards.reverseHoloTrendCents,
                    addedDate = cards.addedDate,
                    boughtPriceCents = cards.boughtPriceCents,
                    isReverseHolo = cards.isReverseHolo
                )
            }
        }
    }

    suspend fun getAllLocalCards(): Result<List<com.tcgbox.database.Cards>> {
        return runCatching {
            withContext(Dispatchers.IO) {
                appDatabase.cardsQueries.getCards().executeAsList()
            }
        }
    }
}