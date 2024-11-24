package hu.bme.aut.android.shoppinglist.domain.model

data class User(
    val id: String = "",
    val email: String = "",
    val sharedLists: Map<String, String> = emptyMap(),
    val requests: List<String> = emptyList(),
    val contacts: List<String> = emptyList()
)