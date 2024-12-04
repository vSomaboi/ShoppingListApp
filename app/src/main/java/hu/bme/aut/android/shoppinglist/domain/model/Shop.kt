package hu.bme.aut.android.shoppinglist.domain.model

data class Shop(
    val id: String = "",
    val name: String = "",
    val numberOfRatings: Int = 0,
    val estimatedWaitingTime: Int = 0
)
