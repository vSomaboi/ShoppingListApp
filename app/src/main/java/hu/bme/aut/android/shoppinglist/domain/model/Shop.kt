package hu.bme.aut.android.shoppinglist.domain.model

data class Shop(
    val id: String = "",
    val name: String = "",
    var numberOfRatings: Int = 0,
    var estimatedWaitingTime: Int = 0
)
