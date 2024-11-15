package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product

class ProvidePriceInfoUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(product: Product, priceInfo: Pair<String, Int>){
        productService.providePriceInfo(product, priceInfo)
    }
}