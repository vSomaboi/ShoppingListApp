package hu.bme.aut.android.shoppinglist.ui.model

import hu.bme.aut.android.shoppinglist.domain.model.PriceAtTimePoint
import hu.bme.aut.android.shoppinglist.domain.model.Product

data class ProductUi(
    val id: String = "",
    val name: String = "",
    val lidlPrices: List<PriceAtTimePoint> = emptyList(),
    val tescoPrices : List<PriceAtTimePoint> = emptyList(),
    val sparPrices : List<PriceAtTimePoint> = emptyList(),
    var selectedAmount: Float = 0f
)

fun ProductUi.asProduct(): Product = Product(
    id= id,
    name = name,
    lidlPrices = lidlPrices,
    tescoPrices = tescoPrices,
    sparPrices = sparPrices,
    selectedAmount = selectedAmount
)

fun Product.asProductUi(): ProductUi = ProductUi(
    id= id,
    name = name,
    lidlPrices = lidlPrices,
    tescoPrices = tescoPrices,
    sparPrices = sparPrices,
    selectedAmount = selectedAmount
)