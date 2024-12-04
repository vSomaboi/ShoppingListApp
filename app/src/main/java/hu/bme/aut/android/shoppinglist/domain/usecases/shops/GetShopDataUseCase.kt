package hu.bme.aut.android.shoppinglist.domain.usecases.shops

import hu.bme.aut.android.shoppinglist.data.shops.ShopService
import hu.bme.aut.android.shoppinglist.domain.model.Shop

class GetShopDataUseCase(
    private val shopService: ShopService
) {
    suspend operator fun invoke(shopName: String) : Result<Shop>{
        return try{
            val res = shopService.getShopData(shopName)
            Result.success(res)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}