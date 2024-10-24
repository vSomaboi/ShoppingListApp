package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.model.ShoppingList

class GetSharedListsOfUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke() : Result<List<ShoppingList>>{
        return try{
            val lists = userService.getSharedListsOfUser()
            Result.success(lists)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}