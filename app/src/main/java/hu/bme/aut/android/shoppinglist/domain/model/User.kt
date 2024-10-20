package hu.bme.aut.android.shoppinglist.domain.model

data class User(
    val firebaseId: String = "",
    val authId: String = "",
    val sharedLists: Map<String, String> = emptyMap()
)