package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product

class GetProductWithExactNameUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(exactName: String) : Result<Product>{
        return try{
            val res = productService.getProductWithExactName(exactName)
            Result.success(res)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}