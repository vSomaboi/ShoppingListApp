package hu.bme.aut.android.shoppinglist.data.products.firebase

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import hu.bme.aut.android.shoppinglist.data.products.ProductService
import hu.bme.aut.android.shoppinglist.domain.model.PriceAtTimePoint
import hu.bme.aut.android.shoppinglist.domain.model.Product
import kotlinx.coroutines.tasks.await
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseProductService @Inject constructor() : ProductService {
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun saveProduct(product: Product): Boolean {
        val identicalNamedProducts: List<FirebaseProduct> =
            fireStore.collection(PRODUCT_COLLECTION)
                .whereEqualTo("name", product.name.trim().uppercase())
                .limit(1)
                .get()
                .await()
                .toObjects<FirebaseProduct>()
        return if(identicalNamedProducts.isEmpty()){
            fireStore.collection(PRODUCT_COLLECTION).add(
                product.asFirebaseProduct().apply {
                    name = name.trim().uppercase()
                }
            ).await()
            true
        }else{
            false
        }
    }

    override suspend fun updateProduct(product: Product) {
        fireStore.collection(PRODUCT_COLLECTION).document(product.id).set(product.asFirebaseProduct()).await()
    }

    override suspend fun deleteProduct(id: String) {
        fireStore.collection(PRODUCT_COLLECTION).document(id).delete().await()
    }

    override suspend fun getAllProducts(): List<Product> {
        return fireStore.collection(PRODUCT_COLLECTION)
            .orderBy("name")
            .limit(10)
            .get()
            .await()
            .toObjects<FirebaseProduct>()
            .map {
                it.asProduct()
            }
            .toList()
    }

    override suspend fun getProductsNamedAs(name: String): List<Product> {
        return fireStore.collection(PRODUCT_COLLECTION)
            .whereGreaterThanOrEqualTo("name", name.uppercase())
            .whereLessThanOrEqualTo("name", name.uppercase() + "Z")
            .orderBy("name")
            .get()
            .await()
            .toObjects<FirebaseProduct>()
            .map {
                it.asProduct()
            }
            .toList()
    }

    override suspend fun getProductsWithNames(names: List<String>): List<Product> {
        return fireStore.collection(PRODUCT_COLLECTION)
            .whereIn("name", names.map { it.uppercase() })
            .get()
            .await()
            .toObjects<FirebaseProduct>()
            .map {
                it.asProduct()
            }
            .toList()
    }

    override suspend fun getProductWithExactName(exactName: String): Product {
        return fireStore.collection(PRODUCT_COLLECTION)
            .whereEqualTo("name", exactName.uppercase())
            .get()
            .await()
            .toObjects<FirebaseProduct>()
            .map {
                it.asProduct()
            }
            .first()
    }

    override suspend fun providePriceInfo(product: Product, priceInfo: Pair<String, Int>) {
        fireStore.collection(PRODUCT_COLLECTION).document(product.id)
            .update("${priceInfo.first.lowercase()}Prices", FieldValue.arrayUnion(
                    PriceAtTimePoint(
                        price = priceInfo.second,
                        dateOfProviding = Timestamp(Date())
                    )
                )
            )
            .await()
    }

    companion object{
        private const val PRODUCT_COLLECTION = "products"
    }
}