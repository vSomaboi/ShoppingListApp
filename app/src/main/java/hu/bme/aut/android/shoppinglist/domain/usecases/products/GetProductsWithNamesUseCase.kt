package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService

class GetProductsWithNamesUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(names: List<String>){
        productService.getProductsWithNames(names)
    }
}