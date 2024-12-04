package hu.bme.aut.android.shoppinglist.domain.usecases.users

import android.util.Log
import hu.bme.aut.android.shoppinglist.data.users.UserService

class GetRequestsOfCurrentUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke() : Result<List<String>>{
        return try{
            val requests = userService.getRequestsOfCurrentUser()
            Result.success(requests)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}