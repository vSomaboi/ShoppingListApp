package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product

class GetProductsNamedAsUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(name: String) : Result<List<Product>>{
        return try{
            val list = productService.getProductsNamedAs(name)
            Result.success(list)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}