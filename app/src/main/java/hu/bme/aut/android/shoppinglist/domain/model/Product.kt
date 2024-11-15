package hu.bme.aut.android.shoppinglist.domain.model

data class Product(
    val id: String = "",
    val name: String = "",
    val lidlPrices: List<PriceAtTimePoint> = emptyList(),
    val tescoPrices : List<PriceAtTimePoint> = emptyList(),
    val sparPrices : List<PriceAtTimePoint> = emptyList(),
    val selectedAmount: Float = 0f
)