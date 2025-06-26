package data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonCardApiResponse(
    val data: ApiCard
)

@Serializable
data class ApiCard(
    val id: String,
    val name: String,
    val images: ApiImages,
    val set: ApiSet,
    val tcgplayer: ApiTcgPlayer
)

@Serializable
data class ApiImages(
    val small: String,
    val large: String
)

@Serializable
data class ApiSet(
    val releaseDate: String
)

@Serializable
data class ApiTcgPlayer(
    val url: String,
    val prices: ApiTcgPlayerPrices
)

@Serializable
data class ApiTcgPlayerPrices(
    val holofoil: ApiHolofoilPrices
)

@Serializable
data class ApiHolofoilPrices(
    @SerialName("market")
    val marketPriceApi: Double
)