package hu.bme.aut.android.shoppinglist.data.requests.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.RequestData

data class FirebaseRequestData(
    @DocumentId val id: String = "",
    val userId: String = "",
    val requesters: List<String> = emptyList()
)

fun FirebaseRequestData.asRequestData() : RequestData = RequestData(
    id = id,
    userId = userId,
    requesters = requesters
)

fun RequestData.asFirebaseRequestData() : FirebaseRequestData = FirebaseRequestData(
    id = id,
    userId = userId,
    requesters = requesters
)