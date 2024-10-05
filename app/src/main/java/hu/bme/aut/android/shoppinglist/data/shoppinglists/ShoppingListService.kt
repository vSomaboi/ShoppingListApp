package hu.bme.aut.android.shoppinglist.data.shoppinglists

import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

interface ShoppingListService {
    suspend fun saveShoppingList(shoppingList: ShoppingList)

    suspend fun updateShoppingList(shoppingList: ShoppingList)

    suspend fun deleteShoppingList(id: String)

    suspend fun getListsOfUser(userId: String): List<ShoppingList>
}