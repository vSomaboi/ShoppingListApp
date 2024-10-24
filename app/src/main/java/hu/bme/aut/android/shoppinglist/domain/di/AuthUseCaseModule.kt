package hu.bme.aut.android.shoppinglist.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.auth.AuthService
import hu.bme.aut.android.shoppinglist.domain.usecases.auth.AuthUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {
    @Provides
    @Singleton
    fun provideAuthUseCases(
        authService: AuthService
    ) : AuthUseCases = AuthUseCases(authService)
}