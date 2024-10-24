package hu.bme.aut.android.shoppinglist.util

import hu.bme.aut.android.shoppinglist.domain.model.Product

interface SelectionDialogUser {
    fun getSearchBarInput() : String
    fun updateSearchBar(input: String)
    fun getItemList() : List<Product>
    fun processSelectionResult(selectedItem: Product)
}