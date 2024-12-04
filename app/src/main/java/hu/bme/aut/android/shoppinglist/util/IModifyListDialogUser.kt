package hu.bme.aut.android.shoppinglist.util

import hu.bme.aut.android.shoppinglist.domain.model.Product

interface IModifyListDialogUser {
    fun getListName() : String
    fun updateListName(value: String)
    fun openSelectionDialog()
    fun getModifiedItemList() : List<Product>
    fun deleteListItem(item: Product)
    fun updateList()
}