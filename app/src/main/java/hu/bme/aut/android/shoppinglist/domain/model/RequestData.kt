package hu.bme.aut.android.shoppinglist.domain.model

data class RequestData(
    val id: String = "",
    val userId: String = "",
    val requesters: List<String> = emptyList()
)
