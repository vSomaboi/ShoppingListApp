package hu.bme.aut.android.shoppinglist.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.usecases.products.ProductUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductUseCaseModule {
    @Provides
    @Singleton
    fun provideProductUseCases(
        productService: ProductService
    ) : ProductUseCases = ProductUseCases(productService)
}