package hu.bme.aut.android.shoppinglist.data.auth.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import hu.bme.aut.android.shoppinglist.data.auth.AuthService
import hu.bme.aut.android.shoppinglist.domain.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseAuthService @Inject constructor() : AuthService {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override val currentUserId: String?
        get() = firebaseAuth.currentUser?.uid
    override val hasUser: Boolean
        get() = firebaseAuth.currentUser != null
    override val currentUser: Flow<User?>
        get() = callbackFlow {
            this.trySend(currentUserId?.let { User(it) })
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) })
                }
            firebaseAuth.addAuthStateListener(listener)
            awaitClose { firebaseAuth.removeAuthStateListener(listener) }
        }

    override suspend fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {  result ->
                val user = result.user
                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(user?.email?.substringBefore('@'))
                    .build()
                user?.updateProfile(profileChangeRequest)
            }.await()
    }

    override suspend fun authenticate(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).await()
    }

    override suspend fun deleteAccount() {
        firebaseAuth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }
}