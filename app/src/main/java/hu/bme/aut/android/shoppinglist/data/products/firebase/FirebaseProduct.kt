package hu.bme.aut.android.shoppinglist.data.products.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.Product

data class FirebaseProduct(
    @DocumentId val id: String = "",
    val name: String = "",
    //The key is the name of a certain shop and the value is the product's price in that shop
    val prices: Map<String, Int> = emptyMap()
)

fun FirebaseProduct.asProduct(): Product = Product(
    id = id,
    name = name,
    prices = prices
)

fun Product.asFirebaseProduct() : FirebaseProduct = FirebaseProduct(
    id = id,
    name = name,
    prices = prices
)