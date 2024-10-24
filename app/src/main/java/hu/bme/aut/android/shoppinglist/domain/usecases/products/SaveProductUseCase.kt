package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product

class SaveProductUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(product: Product){
        productService.saveProduct(product)
    }
}