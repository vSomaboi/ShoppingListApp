package hu.bme.aut.android.shoppinglist.data.connections

import hu.bme.aut.android.shoppinglist.domain.model.ConnectionData

interface ConnectionService {
    suspend fun saveConnection(connection: ConnectionData)

    suspend fun updateConnection(connection: ConnectionData)

    suspend fun deleteConnection(id: String)

    suspend fun getConnectionsOfUser(userId: String) : ConnectionData
}