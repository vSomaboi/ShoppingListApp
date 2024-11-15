package hu.bme.aut.android.shoppinglist.domain.model

import java.time.LocalDate

data class PriceAtTimePoint(
    val price: Int,
    val dateOfProviding: LocalDate
)