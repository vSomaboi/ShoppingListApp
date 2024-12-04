package hu.bme.aut.android.shoppinglist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.shops.ShopService
import hu.bme.aut.android.shoppinglist.data.shops.firebase.FirebaseShopService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShopServiceModule {
    @Binds
    @Singleton
    abstract fun bindShopService(
        firebaseShopService: FirebaseShopService
    ) : ShopService
}