package hu.bme.aut.android.shoppinglist.domain.usecases.products

import hu.bme.aut.android.shoppinglist.data.products.ProductService

class ProductUseCases(
    val saveProduct : SaveProductUseCase,
    val updateProduct : UpdateProductUseCase,
    val deleteProduct : DeleteProductUseCase,
    val getProductsWithNames : GetProductsWithNamesUseCase,
    val getProductsNamedAs : GetProductsNamedAsUseCase,
    val providePriceInfo : ProvidePriceInfoUseCase,
    val getProductWithExactName : GetProductWithExactNameUseCase
)