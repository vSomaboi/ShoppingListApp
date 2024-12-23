package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product

class GetProductsWithNamesUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(names: List<String>) : Result<List<Product>>{
        return try{
            val list = productService.getProductsWithNames(names)
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}