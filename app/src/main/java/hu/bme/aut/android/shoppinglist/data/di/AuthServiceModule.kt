package hu.bme.aut.android.shoppinglist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.auth.AuthService
import hu.bme.aut.android.shoppinglist.data.auth.firebase.FirebaseAuthService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthServiceModule {
    @Binds
    @Singleton
    abstract fun bindAuthService(
        firebaseAuthService: FirebaseAuthService
    ) : AuthService
}