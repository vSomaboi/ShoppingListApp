package hu.bme.aut.android.shoppinglist.data.shops

import hu.bme.aut.android.shoppinglist.domain.model.Shop

interface ShopService {
    suspend fun updateWaitingTime(shopName: String, value: Int)
    suspend fun getShopData(shopName: String) : Shop
}