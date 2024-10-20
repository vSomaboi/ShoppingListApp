package hu.bme.aut.android.shoppinglist.data.users

import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.domain.model.User

interface UserService {
    suspend fun createUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(id: String)

    suspend fun saveOwnList(list: ShoppingList)

    suspend fun updateOwnList(list: ShoppingList)

    suspend fun deleteOwnList(listId: String)

    suspend fun saveSharedList(listRef: Map<String, String>)

    suspend fun updateSharedList(list: ShoppingList)

    suspend fun deleteSharedList(listRef: Map<String, String>)

    suspend fun getOwnListsOfUser() : List<ShoppingList>

    suspend fun getSharedListsOfUser() : List<ShoppingList>
}