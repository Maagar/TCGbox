package data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Card (
    val id: String,
    val name: String,
    val releaseDate: LocalDate,
    val addedDate: LocalDate,
    val imageSmall: String,
    val imageLarge: String,
    val tcgPlayerUrl: String,
    val marketPrice: Long,
    val boughtPrice: Long,
    )