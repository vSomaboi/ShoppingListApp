package hu.bme.aut.android.shoppinglist.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.usecases.users.UserUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {
    @Provides
    @Singleton
    fun provideUserUseCases(
        userService: UserService
    ) : UserUseCases = UserUseCases(userService)
}