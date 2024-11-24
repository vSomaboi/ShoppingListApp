package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class SendRequestUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(targetEmail: String) : Result<Boolean> {
        return try{
            val res = userService.sendRequest(targetEmail)
            Result.success(res)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}