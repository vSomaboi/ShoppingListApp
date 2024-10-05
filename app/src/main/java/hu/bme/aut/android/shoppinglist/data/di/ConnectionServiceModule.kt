package hu.bme.aut.android.shoppinglist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.connections.ConnectionService
import hu.bme.aut.android.shoppinglist.data.connections.firebase.FirebaseConnectionService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ConnectionServiceModule {
    @Binds
    @Singleton
    abstract fun bindConnectionService(
        firebaseConnectionService: FirebaseConnectionService
    ) : ConnectionService
}