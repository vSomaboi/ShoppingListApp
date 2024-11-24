package hu.bme.aut.android.shoppinglist.util

interface IAddProductDialogUser {
    fun getProductName() : String
    fun updateProductName(input: String)
    fun getLidlPrice(): String
    fun updateLidlPrice(input: String)
    fun getTescoPrice(): String
    fun updateTescoPrice(input: String)
    fun getSparPrice(): String
    fun updateSparPrice(input: String)

    fun processProductDialogResult()
}