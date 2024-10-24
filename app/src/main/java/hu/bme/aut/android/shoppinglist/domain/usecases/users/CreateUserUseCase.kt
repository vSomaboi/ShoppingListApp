package hu.bme.aut.android.shoppinglist.domain.usecases.users

import hu.bme.aut.android.shoppinglist.data.users.UserService
import hu.bme.aut.android.shoppinglist.domain.model.User

class CreateUserUseCase(
    private val userService: UserService
) {
    suspend operator fun invoke(user: User){
        userService.createUser(user)
    }
}