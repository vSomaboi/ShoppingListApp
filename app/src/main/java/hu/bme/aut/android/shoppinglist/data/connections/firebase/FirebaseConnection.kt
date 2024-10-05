package hu.bme.aut.android.shoppinglist.data.connections.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.ConnectionData

data class FirebaseConnectionData(
    @DocumentId val id: String = "",
    val userId: String = "",
    val connections: List<String> = emptyList()
)

fun FirebaseConnectionData.asConnectionData() : ConnectionData = ConnectionData(
    id = id,
    userId = userId,
    connections = connections
)

fun ConnectionData.asFirebaseConnectionData() : FirebaseConnectionData = FirebaseConnectionData(
    id = id,
    userId = userId,
    connections = connections
)
