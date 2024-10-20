package hu.bme.aut.android.shoppinglist.domain.model

data class ShoppingList(
    val firebaseId: String = "",
    val ownerFirebaseId: String = "",
    val name: String = "",
    val items: List<Product> = emptyList()
)