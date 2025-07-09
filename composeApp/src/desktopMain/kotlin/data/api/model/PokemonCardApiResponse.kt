package data.api.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object DoubleToLongCentsSerializer : KSerializer<Long> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DoubleToLongCents", PrimitiveKind.DOUBLE)

    override fun deserialize(decoder: Decoder): Long {
        val doubleValue = decoder.decodeDouble()
        return (doubleValue * 100).toLong()
    }

    override fun serialize(encoder: Encoder, value: Long) {
        val doubleValue = value / 100.0
        encoder.encodeDouble(doubleValue)
    }
}

@Serializable
data class SearchCardsApiResponse(
    val data: List<ApiCard>
)

@Serializable
data class PokemonCardApiResponse(
    val data: ApiCard
)

@Serializable
data class ApiCard(
    val id: String,
    val name: String,
    val number: Int,
    val images: ApiImages,
    val set: PokemonSet,
    val cardmarket: CardMarket? = null
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
data class CardMarket(
    val url: String,
    val prices: ApiCardMarketPrices
)

@Serializable
data class ApiCardMarketPrices(
    @Serializable(with = DoubleToLongCentsSerializer::class)
    val trendPrice: Long,
    @Serializable(with = DoubleToLongCentsSerializer::class)
    val reverseHoloTrend: Long
)