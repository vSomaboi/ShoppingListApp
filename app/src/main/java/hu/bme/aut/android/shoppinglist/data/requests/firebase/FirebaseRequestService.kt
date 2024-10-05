package hu.bme.aut.android.shoppinglist.data.requests.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import hu.bme.aut.android.shoppinglist.data.requests.RequestService
import hu.bme.aut.android.shoppinglist.domain.model.RequestData
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRequestService @Inject constructor() : RequestService{
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun saveRequest(requestData: RequestData) {
        fireStore.collection(REQUEST_COLLECTION).add(requestData.asFirebaseRequestData()).await()
    }

    override suspend fun updateRequest(requestData: RequestData) {
        fireStore.collection(REQUEST_COLLECTION).document(requestData.id).set(requestData.asFirebaseRequestData()).await()
    }

    override suspend fun deleteRequest(id: String) {
        fireStore.collection(REQUEST_COLLECTION).document(id).delete().await()
    }

    override suspend fun getRequestsForUser(userId: String): RequestData {
        return fireStore.collection(REQUEST_COLLECTION)
            .whereEqualTo("userId", userId)
            .get()
            .await()
            .toObjects<FirebaseRequestData>()
            .map {
                it.asRequestData()
            }
            .first()
    }

    companion object{
        private const val REQUEST_COLLECTION = "requests"
    }
}