package hu.bme.aut.android.shoppinglist.domain.usecases.shops

import hu.bme.aut.android.shoppinglist.data.shops.ShopService

class UpdateWaitingTimeUseCase(
    private val shopService: ShopService
) {
    suspend operator fun invoke(shopName: String, value: Int){
        shopService.updateWaitingTime(shopName, value)
    }
}