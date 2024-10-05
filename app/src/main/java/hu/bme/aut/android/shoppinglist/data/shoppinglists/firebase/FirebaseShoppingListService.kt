package hu.bme.aut.android.shoppinglist.data.shoppinglists.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import hu.bme.aut.android.shoppinglist.data.shoppinglists.ShoppingListService
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseShoppingListService @Inject constructor() : ShoppingListService {
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override suspend fun saveShoppingList(shoppingList: ShoppingList) {
        fireStore.collection(SHOPPING_LIST_COLLECTION).add(shoppingList.asFirebaseShoppingList()).await()
    }

    override suspend fun updateShoppingList(shoppingList: ShoppingList) {
        fireStore.collection(SHOPPING_LIST_COLLECTION).document(shoppingList.id).set(shoppingList.asFirebaseShoppingList()).await()
    }

    override suspend fun deleteShoppingList(id: String) {
        fireStore.collection(SHOPPING_LIST_COLLECTION).document(id).delete().await()
    }

    override suspend fun getListsOfUser(userId: String): List<ShoppingList> {
        return fireStore.collection(SHOPPING_LIST_COLLECTION)
            .whereArrayContains("contributors", userId)
            .get()
            .await()
            .toObjects<FirebaseShoppingList>()
            .map {
                it.asShoppingList()
            }
            .toList()
    }

    companion object{
        private const val SHOPPING_LIST_COLLECTION = "shopping-lists"
    }
}