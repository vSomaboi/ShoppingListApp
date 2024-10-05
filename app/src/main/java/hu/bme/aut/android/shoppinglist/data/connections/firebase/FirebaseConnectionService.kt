package hu.bme.aut.android.shoppinglist.data.connections.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import hu.bme.aut.android.shoppinglist.data.connections.ConnectionService
import hu.bme.aut.android.shoppinglist.domain.model.ConnectionData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseConnectionService @Inject constructor() : ConnectionService {
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun saveConnection(connection: ConnectionData) {
        fireStore.collection(CONNECTION_COLLECTION).add(connection.asFirebaseConnectionData()).await()
    }

    override suspend fun updateConnection(connection: ConnectionData) {
        fireStore.collection(CONNECTION_COLLECTION).document(connection.id).set(connection.asFirebaseConnectionData()).await()
    }

    override suspend fun deleteConnection(id: String) {
        fireStore.collection(CONNECTION_COLLECTION).document(id).delete().await()
    }

    override suspend fun getConnectionsOfUser(userId: String): ConnectionData {
        return fireStore.collection(CONNECTION_COLLECTION)
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .toObjects<FirebaseConnectionData>()
            .map {
                it.asConnectionData()
            }
            .first()
    }

    companion object{
        private const val CONNECTION_COLLECTION = "connections"
    }
}