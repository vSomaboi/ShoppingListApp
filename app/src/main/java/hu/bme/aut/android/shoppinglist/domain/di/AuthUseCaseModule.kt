package hu.bme.aut.android.shoppinglist.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.bme.aut.android.shoppinglist.data.auth.AuthService
import hu.bme.aut.android.shoppinglist.domain.usecases.auth.AuthUseCases
import hu.bme.aut.android.shoppinglist.domain.usecases.auth.AuthenticateUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.auth.HasUserUseCase
import hu.bme.aut.android.shoppinglist.domain.usecases.auth.RegisterUserUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {
    @Provides
    @Singleton
    fun provideAuthenticateUserUseCase(
        authService: AuthService
    ) : AuthenticateUserUseCase = AuthenticateUserUseCase(authService)
    @Provides
    @Singleton
    fun provideHasUserUserUseCase(
        authService: AuthService
    ) : HasUserUseCase = HasUserUseCase(authService)
    @Provides
    @Singleton
    fun provideRegisterUserUseCase(
        authService: AuthService
    ) : RegisterUserUseCase = RegisterUserUseCase(authService)
    @Provides
    @Singleton
    fun provideAuthUseCases(
        authenticateUser: AuthenticateUserUseCase,
        hasUser: HasUserUseCase,
        registerUser: RegisterUserUseCase
    ) : AuthUseCases = AuthUseCases(
            authenticateUser = authenticateUser,
            hasUser = hasUser,
            registerUser = registerUser
        )
}