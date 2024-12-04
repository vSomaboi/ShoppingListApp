package hu.bme.aut.android.shoppinglist.data.users

import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    suspend fun getRequestsOfCurrentUser(): List<String>
    suspend fun getContactsOfCurrentUser(): List<String>
    suspend fun createUser()
    suspend fun updateUser(user: User)

    suspend fun deleteUser(id: String)

    suspend fun sendRequest(targetEmail: String) : Boolean
    suspend fun declineRequest(senderEmail: String)

    suspend fun addContact(contactEmail: String)

    suspend fun removeContact(contactEmail: String)

    suspend fun saveOwnList(list: ShoppingList)

    suspend fun updateOwnList(list: ShoppingList)

    suspend fun deleteOwnList(listId: String)

    suspend fun saveSharedList(listRef: Map<String, String>)

    suspend fun updateSharedList(list: ShoppingList)

    suspend fun deleteSharedList(listRef: Map<String, String>)

    suspend fun getOwnListsOfUser() : List<ShoppingList>

    suspend fun getSharedListsOfUser() : List<ShoppingList>

    suspend fun shareListWithContact(contactEmail: String, list: ShoppingList)
}