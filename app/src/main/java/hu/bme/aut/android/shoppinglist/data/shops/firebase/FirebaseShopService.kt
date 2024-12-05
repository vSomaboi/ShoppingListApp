package hu.bme.aut.android.shoppinglist.data.shops.firebase

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import hu.bme.aut.android.shoppinglist.data.shops.ShopService
import hu.bme.aut.android.shoppinglist.domain.model.Shop
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseShopService @Inject constructor(
    private val fireStore: FirebaseFirestore
) : ShopService {
    override suspend fun updateWaitingTime(shopName: String, value: Int) {
        val currentShopData = fireStore.collection(SHOP_COLLECTION)
            .document(shopName.uppercase())
            .get()
            .await()
            .toObject<FirebaseShop>()
            ?.asShop()

        val newWaitingTime = (value + currentShopData!!.estimatedWaitingTime * currentShopData.numberOfRatings) / (currentShopData.numberOfRatings+1)

        fireStore.collection(SHOP_COLLECTION)
            .document(shopName.uppercase())
            .update("estimatedWaitingTime", newWaitingTime,
                "numberOfRatings", FieldValue.increment(1))
            .await()
    }

    override suspend fun getShopData(shopName: String): Shop {
        return fireStore.collection(SHOP_COLLECTION)
            .document(shopName.uppercase())
            .get()
            .await()
            .toObject<FirebaseShop>()
            ?.asShop() ?: Shop()
    }

    companion object{
        private const val SHOP_COLLECTION = "shops"
    }
}

