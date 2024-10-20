package hu.bme.aut.android.shoppinglist.data.products

import hu.bme.aut.android.shoppinglist.domain.model.Product

interface ProductService {
    suspend fun saveProduct(product: Product)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(id: String)

    suspend fun getProductsWithNames(names: List<String>) : List<Product>
}