package hu.bme.aut.android.shoppinglist.ui.model

import hu.bme.aut.android.shoppinglist.domain.model.PriceAtTimePoint
import hu.bme.aut.android.shoppinglist.domain.model.Product
import hu.bme.aut.android.shoppinglist.util.upperFirst
import kotlin.math.pow

data class ProductUi(
    val id: String = "",
    val name: String = "",
    val lidlPrices: List<PriceAtTimePoint> = emptyList(),
    val tescoPrices : List<PriceAtTimePoint> = emptyList(),
    val sparPrices : List<PriceAtTimePoint> = emptyList(),
    var selectedAmountWholePart: Int = 0,
    var selectedAmountFractionPart: Int = 0
)

fun ProductUi.asProduct(): Product = Product(
    id= id,
    name = name.upperFirst(),
    lidlPrices = lidlPrices,
    tescoPrices = tescoPrices,
    sparPrices = sparPrices,
    selectedAmount = selectedAmountWholePart +
            selectedAmountFractionPart.toFloat()/
            10f.pow(selectedAmountFractionPart.toString().length)
)

fun Product.asProductUi(): ProductUi = ProductUi(
    id= id,
    name = name.upperFirst(),
    lidlPrices = lidlPrices,
    tescoPrices = tescoPrices,
    sparPrices = sparPrices,
    selectedAmountWholePart = selectedAmount.toInt(),
    selectedAmountFractionPart = ((selectedAmount - selectedAmount.toInt())*100).toInt()
)