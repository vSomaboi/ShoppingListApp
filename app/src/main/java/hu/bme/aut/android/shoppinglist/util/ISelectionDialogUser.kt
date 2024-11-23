package hu.bme.aut.android.shoppinglist.util

import hu.bme.aut.android.shoppinglist.ui.model.ProductUi

interface ISelectionDialogUser {
    fun getSearchBarInput() : String
    fun updateSearchBar(input: String)
    fun getItemList() : List<ProductUi>
    fun changeSelectedAmountWholePart(item: ProductUi, diff: Int)
    fun setSelectedAmountWholePart(item: ProductUi, newAmount: String)
    fun changeSelectedAmountFractionPart(item: ProductUi, diff: Int)
    fun setSelectedAmountFractionPart(item: ProductUi, newAmount: String)
    fun processSelectionResult(selectedItem: ProductUi)
}