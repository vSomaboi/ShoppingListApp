package hu.bme.aut.android.shoppinglist.data.shoppinglists.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

data class FirebaseShoppingList(
    @DocumentId val id: String = "",
    val publisherId: String = "",
    val name: String = "",
    val contributors: List<String> = emptyList()
)

fun FirebaseShoppingList.asShoppingList() : ShoppingList = ShoppingList(
    id = id,
    userId = publisherId,
    name = name,
    contributors = contributors
)

fun ShoppingList.asFirebaseShoppingList() : FirebaseShoppingList = FirebaseShoppingList(
    id = id,
    publisherId = userId,
    name = name,
    contributors = contributors
)
