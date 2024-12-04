package hu.bme.aut.android.shoppinglist.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.shops.ShopService
import hu.bme.aut.android.shoppinglist.domain.usecases.shops.GetShopDataUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.shops.ShopUseCases
import hu.bme.aut.android.shoppinglist.domain.usecases.shops.UpdateWaitingTimeUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShopUseCaseModule {
    @Provides
    @Singleton
    fun provideUpdateWaitingTimeUseCase(
        shopService: ShopService
    ) : UpdateWaitingTimeUseCase = UpdateWaitingTimeUseCase(shopService)
    @Provides
    @Singleton
    fun provideGetShopDataUseCase(
        shopService: ShopService
    ) : GetShopDataUseCase = GetShopDataUseCase(shopService)

    @Provides
    @Singleton
    fun provideShopUseCases(
        updateWaitingTime: UpdateWaitingTimeUseCase,
        getShopData: GetShopDataUseCase
    ) : ShopUseCases = ShopUseCases(
        updateWaitingTime = updateWaitingTime,
        getShopData = getShopData
    )
}