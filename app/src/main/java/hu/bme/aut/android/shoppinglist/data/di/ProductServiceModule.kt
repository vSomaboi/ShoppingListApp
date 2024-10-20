package hu.bme.aut.android.shoppinglist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.data.products.firebase.FirebaseProductService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductServiceModule {
    @Binds
    @Singleton
    abstract fun bindProductService(
        firebaseProductService: FirebaseProductService
    ) : ProductService
}