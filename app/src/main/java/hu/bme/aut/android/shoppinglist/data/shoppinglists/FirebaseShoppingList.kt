package hu.bme.aut.android.shoppinglist.data.shoppinglists

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.Product
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

data class FirebaseShoppingList(
    @DocumentId val id: String = "",
    val ownerId: String = "",
    val name: String = "",
    val items: List<Product> = emptyList()
)

fun FirebaseShoppingList.asShoppingList(): ShoppingList = ShoppingList(
    firebaseId = id,
    ownerFirebaseId = ownerId,
    name = name,
    items = items
)

fun ShoppingList.asFirebaseShoppingList(): FirebaseShoppingList = FirebaseShoppingList(
    id = firebaseId,
    ownerId = ownerFirebaseId,
    name = name,
    items = items
)