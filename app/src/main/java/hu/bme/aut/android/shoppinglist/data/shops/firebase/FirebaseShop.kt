package hu.bme.aut.android.shoppinglist.data.shops.firebase

import com.google.firebase.firestore.DocumentId
import hu.bme.aut.android.shoppinglist.domain.model.Shop

data class FirebaseShop(
    @DocumentId val id: String = "",
    val name: String = "",
    val numberOfRatings: Int = 0,
    val estimatedWaitingTime: Int = 0
)

fun FirebaseShop.asShop() : Shop = Shop(
    id = id,
    name = name,
    numberOfRatings = numberOfRatings,
    estimatedWaitingTime = estimatedWaitingTime
)

fun Shop.asFirebaseShop() : FirebaseShop = FirebaseShop(
    id = id,
    name = name,
    numberOfRatings = numberOfRatings,
    estimatedWaitingTime = estimatedWaitingTime
)