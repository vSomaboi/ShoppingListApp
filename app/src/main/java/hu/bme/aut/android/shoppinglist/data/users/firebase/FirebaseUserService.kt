package hu.bme.aut.android.shoppinglist.data.users.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import hu.bme.aut.android.shoppinglist.data.shoppinglists.FirebaseShoppingList
import hu.bme.aut.android.shoppinglist.data.shoppinglists.asFirebaseShoppingList
import hu.bme.aut.android.shoppinglist.data.shoppinglists.asShoppingList
import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList
import hu.bme.aut.android.shoppinglist.domain.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserService @Inject constructor() : UserService {
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val currentUser = firebaseAuth.currentUser
    private val currentUserId = currentUser?.uid



    override suspend fun getRequestsOfCurrentUser(): List<String> {
        return fireStore.collection(USER_COLLECTION)
            .document(currentUserId!!)
            .get()
            .await()
            .toObject<FirebaseUser>()
            ?.requests ?: emptyList()
    }

    override suspend fun getContactsOfCurrentUser(): List<String> {
        return fireStore.collection(USER_COLLECTION)
            .document(currentUserId!!)
            .get()
            .await()
            .toObject<FirebaseUser>()
            ?.contacts ?: emptyList()
    }

    override suspend fun createUser() {
        val newUser = User(
            id = currentUserId!!,
            email = currentUser!!.email!!,
        )
        val identicalNamedUsers = fireStore.collection(USER_COLLECTION)
            .whereEqualTo("email", newUser.email)
            .limit(1)
            .get()
            .await()
            .toObjects<FirebaseUser>()
        if(identicalNamedUsers.isEmpty()){
            fireStore.collection(USER_COLLECTION).document(currentUserId).set(newUser.asFirebaseUser()).await()
        }
    }

    override suspend fun sendRequest(targetEmail: String) : Boolean {
        val targetUser = fireStore.collection(USER_COLLECTION)
            .whereEqualTo("email", targetEmail)
            .limit(1)
            .get()
            .await()
            .toObjects<FirebaseUser>()
        return if(targetUser.isNotEmpty()){
            fireStore.collection(USER_COLLECTION)
                .document(targetUser.first().id)
                .update("requests", FieldValue.arrayUnion(currentUser!!.email)).await()
            true
        }else{
            false
        }
    }

    override suspend fun declineRequest(senderEmail: String) {
        fireStore.collection(USER_COLLECTION)
            .document(currentUserId!!)
            .update("requests", FieldValue.arrayRemove(senderEmail))
            .await()
    }

    override suspend fun addContact(contactEmail: String) {
        fireStore.collection(USER_COLLECTION)
            .document(currentUserId!!)
            .update("contacts", FieldValue.arrayUnion(contactEmail),
                "requests", FieldValue.arrayRemove(contactEmail))
            .await()

        val contact = fireStore.collection(USER_COLLECTION)
            .whereEqualTo("email", contactEmail)
            .limit(1)
            .get()
            .await()
            .toObjects<FirebaseUser>()

        if(contact.isNotEmpty()){
            fireStore.collection(USER_COLLECTION)
                .document(contact.first().id)
                .update("contacts", FieldValue.arrayUnion(currentUser!!.email))
                .await()
        }
    }

    override suspend fun removeContact(contactEmail: String) {
        fireStore.collection(USER_COLLECTION)
            .document(currentUserId!!)
            .update("contacts", FieldValue.arrayRemove(contactEmail))
            .await()

        val contact = fireStore.collection(USER_COLLECTION)
            .whereEqualTo("email", contactEmail)
            .limit(1)
            .get()
            .await()
            .toObjects<FirebaseUser>()

        if(contact.isNotEmpty()){
            fireStore.collection(USER_COLLECTION)
                .document(contact.first().id)
                .update("contacts", FieldValue.arrayRemove(currentUser!!.email))
                .await()
        }

    }

    override suspend fun saveOwnList(list: ShoppingList) {
        fireStore.collection(USER_COLLECTION).document(currentUserId!!)
            .collection(USER_SHOPPING_LIST_COLLECTION)
            .add(
                list.asFirebaseShoppingList().copy(
                    ownerId = currentUserId
                )
            ).await()
    }

    override suspend fun updateOwnList(list: ShoppingList) {
        fireStore.collection(USER_COLLECTION).document(currentUserId!!)
            .collection(USER_SHOPPING_LIST_COLLECTION).document(list.firebaseId)
            .set(list.asFirebaseShoppingList(), SetOptions.mergeFields("items", "name")).await()
    }

    override suspend fun deleteOwnList(listId: String) {
        fireStore.collection(USER_COLLECTION).document(currentUserId!!)
            .collection(USER_SHOPPING_LIST_COLLECTION).document(listId).delete().await()
    }

    override suspend fun saveSharedList(listRef: Map<String, String>) {
        fireStore.collection(USER_COLLECTION).document(currentUserId!!)
            .update("sharedLists", FieldValue.arrayUnion(listRef)).await()
    }

    override suspend fun updateSharedList(list: ShoppingList) {
        fireStore.collection(USER_COLLECTION).document(list.ownerFirebaseId)
            .collection(USER_SHOPPING_LIST_COLLECTION).document(list.firebaseId)
            .update("items", list.items).await()
    }

    override suspend fun deleteSharedList(listRef: Map<String, String>) {
        fireStore.collection(USER_COLLECTION).document(currentUserId!!)
            .update("sharedLists", FieldValue.arrayRemove(listRef)).await()
    }

    override suspend fun updateUser(user: User) {
        fireStore.collection(USER_COLLECTION).document(user.id)
            .set(user.asFirebaseUser(), SetOptions.merge()).await()
    }

    override suspend fun deleteUser(id: String) {
        fireStore.collection(USER_COLLECTION).document(id).delete().await()
    }

    override suspend fun getOwnListsOfUser(): List<ShoppingList> {
        return fireStore.collection(USER_COLLECTION).document(currentUserId!!)
            .collection(USER_SHOPPING_LIST_COLLECTION)
            .get()
            .await()
            .toObjects<FirebaseShoppingList>()
            .map {
                it.asShoppingList()
            }
            .toList()
    }

    override suspend fun getSharedListsOfUser(): List<ShoppingList> {
        val result : MutableList<ShoppingList> = mutableListOf()
        val currentUser = fireStore.collection(USER_COLLECTION)
            .document(currentUserId!!)
            .get()
            .await()
            .toObject<FirebaseUser>()!!
            .asUser()
        
        currentUser.sharedLists.forEach { listRef ->
            result.add(
                fireStore.collection(USER_COLLECTION).document(listRef.key)
                    .collection(USER_SHOPPING_LIST_COLLECTION).document(listRef.value)
                    .get()
                    .await()
                    .toObject(FirebaseShoppingList::class.java)
                    !!.asShoppingList()
            )
        }
        return result
    }

    override suspend fun shareListWithContact(contactEmail: String, list: ShoppingList) {
        val contactId = fireStore.collection(USER_COLLECTION)
            .whereEqualTo("email", contactEmail)
            .get()
            .await()
            .toObjects<FirebaseUser>()
            .first()
            .id
        fireStore.collection(USER_COLLECTION).document(contactId)
            .update("sharedLists", FieldValue.arrayUnion(mapOf(Pair(currentUserId, list.firebaseId))))
            .await()
    }

    companion object{
        private const val USER_COLLECTION = "users"
        private const val USER_SHOPPING_LIST_COLLECTION = "user_shopping_lists"
    }
}