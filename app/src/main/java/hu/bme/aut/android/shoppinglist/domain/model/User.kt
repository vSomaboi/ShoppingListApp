package hu.bme.aut.android.shoppinglist.domain.model

data class User(
    val id: String = "",
    val email: String = "",
    val sharedLists: Map<String, String> = emptyMap()
)