package hu.bme.aut.android.shoppinglist.util

import hu.bme.aut.android.shoppinglist.ui.model.ProductUi

interface ISelectionDialogUser {
    fun getSearchBarInput() : String
    fun updateSearchBar(input: String)
    fun getItemList() : List<ProductUi>

    fun changeSelectedAmount(item: ProductUi, diff: Int)

    fun setSelectedAmount(item: ProductUi, newAmount: Float)
    fun processSelectionResult(selectedItem: ProductUi)
}