package hu.bme.aut.android.shoppinglist.util

import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

interface IShareDialogUser {
    fun getShoppingLists() : List<ShoppingList>
    fun listSelected(list: ShoppingList)
}