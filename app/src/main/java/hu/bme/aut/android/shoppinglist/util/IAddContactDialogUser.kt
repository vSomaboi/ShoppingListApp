package hu.bme.aut.android.shoppinglist.util

interface IAddContactDialogUser {
    fun getContactEmail() : String
    fun updateContactEmail(input: String)

    fun processContactDialogResult()
}