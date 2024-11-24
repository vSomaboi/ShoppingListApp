package hu.bme.aut.android.shoppinglist.domain.usecases.auth

import hu.bme.aut.android.shoppinglist.data.auth.AuthService

class HasUserUseCase(
    private val authService: AuthService
) {
    suspend operator fun invoke() : Result<Boolean>{
        return try{
            val res = authService.hasUser
            Result.success(res)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}