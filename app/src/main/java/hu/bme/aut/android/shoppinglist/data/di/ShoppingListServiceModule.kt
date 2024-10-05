package hu.bme.aut.android.shoppinglist.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.shoppinglists.ShoppingListService
import hu.bme.aut.android.shoppinglist.data.shoppinglists.firebase.FirebaseShoppingListService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShoppingListServiceModule {
    @Binds
    @Singleton
    abstract fun bindShoppingListService(
        firebaseShoppingListService: FirebaseShoppingListService
    ) : ShoppingListService
}