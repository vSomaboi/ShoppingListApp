package hu.bme.aut.android.shoppinglist.domain.model

data class ConnectionData(
    val id: String = "",
    val userId: String = "",
    val connections: List<String> = emptyList()
)