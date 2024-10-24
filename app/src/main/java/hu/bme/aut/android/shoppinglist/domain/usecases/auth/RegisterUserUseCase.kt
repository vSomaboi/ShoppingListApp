package hu.bme.aut.android.shoppinglist.domain.usecases.auth

import hu.bme.aut.android.shoppinglist.data.auth.AuthService

class RegisterUserUseCase(
    private val authService: AuthService
) {
    suspend operator fun invoke(email: String, password: String){
        authService.signUp(email, password)
    }
}