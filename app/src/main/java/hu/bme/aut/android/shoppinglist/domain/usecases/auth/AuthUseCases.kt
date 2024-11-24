package hu.bme.aut.android.shoppinglist.domain.usecases.auth

import hu.bme.aut.android.shoppinglist.data.auth.AuthService

class AuthUseCases(
    authService: AuthService
) {
    val authenticateUser = AuthenticateUserUseCase(authService)
    val registerUser = RegisterUserUseCase(authService)
    val hasUser = HasUserUseCase(authService)
}