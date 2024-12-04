package hu.bme.aut.android.shoppinglist.util

fun String.upperFirst() : String = lowercase().replaceFirstChar { c -> c.uppercase() }