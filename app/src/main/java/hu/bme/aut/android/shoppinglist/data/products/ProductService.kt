package hu.bme.aut.android.shoppinglist.data.products

import hu.bme.aut.android.shoppinglist.domain.model.Product

interface ProductService {
    suspend fun saveProduct(product: Product): Boolean

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(id: String)

    suspend fun getAllProducts() : List<Product>

    suspend fun getProductsNamedAs(name: String) : List<Product>

    suspend fun getProductsWithNames(names: List<String>) : List<Product>

    suspend fun getProductWithExactName(exactName: String) : Product

    suspend fun providePriceInfo(productName: String, priceInfo: Pair<String, Int>) : Boolean
}