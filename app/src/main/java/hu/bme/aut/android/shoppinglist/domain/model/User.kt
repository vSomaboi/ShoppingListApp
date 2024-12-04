package hu.bme.aut.android.shoppinglist.domain.model

data class User(
    val id: String = "",
    val email: String = "",
    val ownLists: List<ShoppingList> = emptyList(),
    val sharedLists: List<SharedListReference> = emptyList(),
    val requests: List<String> = emptyList(),
    val contacts: List<String> = emptyList()
)