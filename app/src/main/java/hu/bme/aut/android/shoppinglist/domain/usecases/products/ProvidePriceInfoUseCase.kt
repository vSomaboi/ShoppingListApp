package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product

class ProvidePriceInfoUseCase(
    private val productService: ProductService
) {
    suspend operator fun invoke(productName: String, priceInfo: Pair<String, Int>) : Result<Boolean> {
        return try{
            val res = productService.providePriceInfo(productName, priceInfo)
            Result.success(res)
        }catch (e: Exception){
            Result.failure(e)
        }

    }
}