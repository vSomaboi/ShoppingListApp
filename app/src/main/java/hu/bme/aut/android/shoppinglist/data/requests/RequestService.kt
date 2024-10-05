package hu.bme.aut.android.shoppinglist.data.requests

import hu.bme.aut.android.shoppinglist.domain.model.RequestData

interface RequestService {
    suspend fun saveRequest(requestData: RequestData)

    suspend fun updateRequest(requestData: RequestData)
    suspend fun deleteRequest(id: String)
    suspend fun getRequestsForUser(userId: String) : RequestData
}