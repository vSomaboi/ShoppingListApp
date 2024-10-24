package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService

class DeleteUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(id: String){
        userService.deleteUser(id)
    }
}