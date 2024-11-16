package hu.bme.aut.android.shoppinglist.data.products.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.PriceAtTimePoint
import hu.bme.aut.android.shoppinglist.domain.model.Product

data class FirebaseProduct(
    @DocumentId val id: String = "",
    var name: String = "",
    val lidlPrices: List<PriceAtTimePoint> = emptyList(),
    val tescoPrices : List<PriceAtTimePoint> = emptyList(),
    val sparPrices : List<PriceAtTimePoint> = emptyList(),
)

fun FirebaseProduct.asProduct(): Product = Product(
    id = id,
    name = name,
    lidlPrices = lidlPrices,
    tescoPrices = tescoPrices,
    sparPrices = sparPrices
)

fun Product.asFirebaseProduct() : FirebaseProduct = FirebaseProduct(
    id = id,
    name = name,
    lidlPrices = lidlPrices,
    tescoPrices = tescoPrices,
    sparPrices = sparPrices
)