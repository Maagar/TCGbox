package data.model

import kotlinx.datetime.LocalDate

data class Card (
    val id: String,
    val name: String,
    val releaseDate: LocalDate,
    val imageSmall: String,
    val imageLarge: String,
    val tcgPlayerUrl: String,
    val marketPrice: Double,
    val boughtPrice: Double,
    )