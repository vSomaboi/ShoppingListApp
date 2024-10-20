package hu.bme.aut.android.shoppinglist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.data.users.firebase.FirebaseUserService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UserServiceModule {
    @Singleton
    @Binds
    abstract fun bindUserService(
        firebaseUserService: FirebaseUserService
    ) : UserService
}