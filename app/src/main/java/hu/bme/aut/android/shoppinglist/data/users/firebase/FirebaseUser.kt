package hu.bme.aut.android.shoppinglist.data.users.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.domain.model.User

data class FirebaseUser(
    @DocumentId val id: String = "",
    val authId: String = "",
    val sharedLists: Map<String, String> = emptyMap()
)

fun FirebaseUser.asUser() : User = User(
    firebaseId = id,
    authId = authId,
    sharedLists = sharedLists
)

fun User.asFirebaseUser(): FirebaseUser = FirebaseUser(
    id = firebaseId,
    authId = authId,
    sharedLists = sharedLists
)