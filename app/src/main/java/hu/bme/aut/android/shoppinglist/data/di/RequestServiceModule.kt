package hu.bme.aut.android.shoppinglist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.requests.RequestService
import hu.bme.aut.android.shoppinglist.data.requests.firebase.FirebaseRequestService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RequestServiceModule {
    @Binds
    @Singleton
    abstract fun bindRequestService(
        firebaseRequestService: FirebaseRequestService
    ) : RequestService
}