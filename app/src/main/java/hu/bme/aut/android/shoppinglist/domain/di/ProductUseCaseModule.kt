package hu.bme.aut.android.shoppinglist.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.usecases.products.DeleteProductUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.products.GetProductWithExactNameUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.products.GetProductsNamedAsUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.products.GetProductsWithNamesUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.products.ProductUseCases
import hu.bme.aut.android.shoppinglist.domain.usecases.products.ProvidePriceInfoUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.products.SaveProductUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.products.UpdateProductUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductUseCaseModule {
    @Provides
    @Singleton
    fun provideDeleteProductUseCase(
        productService: ProductService
    ) : DeleteProductUseCase = DeleteProductUseCase(productService)
    @Provides
    @Singleton
    fun provideGetProductsNamedAsUseCase(
        productService: ProductService
    ) : GetProductsNamedAsUseCase = GetProductsNamedAsUseCase(productService)
    @Provides
    @Singleton
    fun provideGetProductsWithNamesUseCase(
        productService: ProductService
    ) : GetProductsWithNamesUseCase = GetProductsWithNamesUseCase(productService)
    @Provides
    @Singleton
    fun provideGetProductWithExactNameUseCase(
        productService: ProductService
    ) : GetProductWithExactNameUseCase = GetProductWithExactNameUseCase(productService)
    @Provides
    @Singleton
    fun provideProvidePriceInfoUseCase(
        productService: ProductService
    ) : ProvidePriceInfoUseCase = ProvidePriceInfoUseCase(productService)
    @Provides
    @Singleton
    fun provideSaveProductUseCase(
        productService: ProductService
    ) : SaveProductUseCase = SaveProductUseCase(productService)
    @Provides
    @Singleton
    fun provideUpdateProductUseCase(
        productService: ProductService
    ) : UpdateProductUseCase = UpdateProductUseCase(productService)
    @Provides
    @Singleton
    fun provideProductUseCases(
        saveProduct: SaveProductUseCase,
        updateProduct: UpdateProductUseCase,
        deleteProduct: DeleteProductUseCase,
        getProductsWithNames: GetProductsWithNamesUseCase,
        getProductsNamedAs: GetProductsNamedAsUseCase,
        providePriceInfo: ProvidePriceInfoUseCase,
        getProductWithExactName: GetProductWithExactNameUseCase
    ) : ProductUseCases = ProductUseCases(
        saveProduct = saveProduct,
        updateProduct = updateProduct,
        deleteProduct = deleteProduct,
        getProductsWithNames = getProductsWithNames,
        getProductsNamedAs = getProductsNamedAs,
        providePriceInfo = providePriceInfo,
        getProductWithExactName = getProductWithExactName
    )
}