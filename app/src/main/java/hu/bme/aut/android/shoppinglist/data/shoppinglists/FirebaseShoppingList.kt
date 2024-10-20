package hu.bme.aut.android.shoppinglist.data.shoppinglists

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.data.products.firebase.FirebaseProduct
import hu.bme.aut.android.shoppinglist.data.products.firebase.asFirebaseProduct
import hu.bme.aut.android.shoppinglist.data.products.firebase.asProduct
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

data class FirebaseShoppingList(
    @DocumentId val id: String = "",
    val ownerId: String = "",
    val name: String = "",
    val items: List<FirebaseProduct> = emptyList()
)

fun FirebaseShoppingList.asShoppingList(): ShoppingList = ShoppingList(
    firebaseId = id,
    ownerFirebaseId = ownerId,
    name = name,
    items = items.map { it.asProduct() }
)

fun ShoppingList.asFirebaseShoppingList(): FirebaseShoppingList = FirebaseShoppingList(
    id = firebaseId,
    ownerId = ownerFirebaseId,
    name = name,
    items = items.map { it.asFirebaseProduct() }
)