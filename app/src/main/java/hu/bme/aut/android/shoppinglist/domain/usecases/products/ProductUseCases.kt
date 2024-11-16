package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService

class ProductUseCases(
    productService: ProductService
) {
    val saveProduct = SaveProductUseCase(productService)
    val updateProduct = UpdateProductUseCase(productService)
    val deleteProduct = DeleteProductUseCase(productService)
    val getProductsWithNames = GetProductsWithNamesUseCase(productService)
    val getProductsNamedAsUseCase = GetProductsNamedAsUseCase(productService)
    val providePriceInfoUseCase = ProvidePriceInfoUseCase(productService)
    val getProductWithExactNameUseCase = GetProductWithExactNameUseCase(productService)
}