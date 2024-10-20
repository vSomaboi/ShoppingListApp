package hu.bme.aut.android.shoppinglist.data.products.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.Product
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseProductService @Inject constructor() : ProductService {
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun saveProduct(product: Product) {
        fireStore.collection(PRODUCT_COLLECTION).add(product.asFirebaseProduct()).await()
    }

    override suspend fun updateProduct(product: Product) {
        fireStore.collection(PRODUCT_COLLECTION).document(product.id).set(product.asFirebaseProduct()).await()
    }

    override suspend fun deleteProduct(id: String) {
        fireStore.collection(PRODUCT_COLLECTION).document(id).delete().await()
    }

    override suspend fun getProductsWithNames(names: List<String>): List<Product> {
        return fireStore.collection(PRODUCT_COLLECTION)
            .whereIn("name", names)
            .get()
            .await()
            .toObjects<FirebaseProduct>()
            .map {
                it.asProduct()
            }
            .toList()
    }

    companion object{
        private const val PRODUCT_COLLECTION = "products"
    }
}