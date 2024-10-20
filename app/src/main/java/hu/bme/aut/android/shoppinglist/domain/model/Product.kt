package hu.bme.aut.android.shoppinglist.domain.model

data class Product(
    val id: String = "",
    val name: String = "",
    //The key is the name of a certain shop and the value is the product's price in that shop
    val prices: Map<String, Int> = emptyMap()
)