package data.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Card (
    val id: String,
    val name: String,
    val releaseDate: LocalDate,
    val addedDate: LocalDate? = null,
    val imageSmall: String,
    val imageLarge: String,
    val cardmarketURL: String,
    val marketPrice: Long,
    val boughtPrice: Long? = null,
    )