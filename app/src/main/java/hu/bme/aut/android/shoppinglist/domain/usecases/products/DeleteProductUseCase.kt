package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService

class DeleteProductUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(id: String){
        productService.deleteProduct(id)
    }
}