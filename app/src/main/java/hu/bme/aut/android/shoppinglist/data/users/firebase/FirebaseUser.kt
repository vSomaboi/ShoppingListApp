package hu.bme.aut.android.shoppinglist.data.users.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.User

data class FirebaseUser(
    @DocumentId val id: String = "",
    val email: String = "",
    val sharedLists: Map<String, String> = emptyMap(),
    val requests: List<String> = emptyList(),
    val contacts: List<String> = emptyList()
)

fun FirebaseUser.asUser() : User = User(
    id = id,
    email = email,
    sharedLists = sharedLists,
    requests = requests,
    contacts = contacts
)

fun User.asFirebaseUser(): FirebaseUser = FirebaseUser(
    id = id,
    email = email,
    sharedLists = sharedLists,
    requests = requests,
    contacts = contacts
)