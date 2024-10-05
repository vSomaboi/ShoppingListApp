package hu.bme.aut.android.shoppinglist.domain.model

data class ShoppingList(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val contributors: List<String> = emptyList()
)