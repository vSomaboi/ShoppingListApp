package hu.bme.aut.android.shoppinglist.util

interface IProvidePriceDialogUser {
    fun getSearchbarInput(): String
    fun updateSearchbarInput(value: String)
    fun getLidlPrice() : String
    fun updateLidlPrice(value: String)
    fun getTescoPrice() : String
    fun updateTescoPrice(value: String)
    fun getSparPrice() : String
    fun updateSparPrice(value: String)
    fun processInfoDialogResult()
}