package data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PokemonSetsApiResponse(
    val data: List<PokemonSet>
)

@Serializable
data class PokemonSet(
    val id: String,
    val name: String,
    val printedTotal: Int
)