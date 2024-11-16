package hu.bme.aut.android.shoppinglist.util

interface IAddProductDialogUser {
    fun getProductName() : String
    fun updateName(input: String)
    fun getLidlPrice() : Int
    fun updateLidlPrice(input: Int)
    fun getTescoPrice() : Int
    fun updateTescoPrice(input: Int)
    fun getSparPrice() : Int
    fun updateSparPrice(input: Int)

    fun processDialogResult()
}