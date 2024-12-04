package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class GetContactsOfCurrentUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke() : Result<List<String>>{
        return try{
            val contacts = userService.getContactsOfCurrentUser()
            Result.success(contacts)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}